package com.sprint.mission.guestbookservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
public class FileService {
    private final S3Client s3Client; // S3 서비스 의존성 주입
    private final FileRepository fileRepository;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Value("${aws.s3.base-url}")
    private String baseUrl;

    public FileService(S3Client s3Client, FileRepository fileRepository) {
        this.s3Client = s3Client;
        this.fileRepository = fileRepository;
    }

    public FileEntity uploadFile(MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            String contentType = file.getContentType();
            long size = file.getSize();

            // S3에 저장할 고유한 키 생성
            String s3Key = UUID.randomUUID() + "-" + fileName;

            // S3에 파일 업로드
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(s3Key)
                    .contentType(contentType)
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), size));

            // S3 URL 생성
            String s3Url = baseUrl + "/" + s3Key;

            // DB에 파일 정보 저장
            FileEntity fileEntity = new FileEntity(fileName, contentType, s3Url, s3Key, size);
            fileEntity = fileRepository.save(fileEntity);

            log.info("File uploaded successfully: {}", s3Key);

            return fileEntity;

        } catch (IOException e) {
            log.error("Failed to upload file", e);
            throw new RuntimeException("Failed to upload file", e);
        }
    }
}
