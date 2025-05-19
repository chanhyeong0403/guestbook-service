package com.sprint.mission.guestbookservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class GuestBookService {
    private final GuestBookRepository guestBookRepository;
    private final FileService fileService; // 파일 서비스 의존성 주입

    public GuestBookResponseDto createGuestbook(String name, String title, String content, MultipartFile image) {
        log.info("Creating guestbook entry with name: {}, title: {}, content: {}", name, title, content);

        FileEntity fileEntity = null;
        if (image != null && !image.isEmpty()) {
            fileEntity = fileService.uploadFile(image);
        }

        GuestBook guestBook = new GuestBook(name, title, content, fileEntity);
        guestBookRepository.save(guestBook); // 실제 DB 저장 로직

        String imageUrl = (fileEntity != null) ? fileEntity.getS3Url() : null;

        return new GuestBookResponseDto(guestBook.getId(), name, title, content, imageUrl, guestBook.getCreatedAt());
    }

    public GuestBookResponseDto getGuestbook(Long id) {
        log.info("Retrieving guestbook entry with id: {}", id);
        GuestBook guestBook = guestBookRepository.findById(id).orElseThrow(() -> new RuntimeException("Guestbook not found"));

        String imageUrl = (guestBook.getFile() != null) ? guestBook.getFile().getS3Url() : null;

        return new GuestBookResponseDto(guestBook.getId(), guestBook.getName(), guestBook.getTitle(), guestBook.getContent(), imageUrl, guestBook.getCreatedAt());
    }

    public Page<GuestBookResponseDto> getGuestbookList(PageRequest pageRequest) {
        log.info("Retrieving guestbook list with page: {}, size: {}", pageRequest.getPageNumber(), pageRequest.getPageSize());
        Page<GuestBook> guestBookPage = guestBookRepository.findAll(pageRequest);
        return guestBookPage.map(guestBook -> new GuestBookResponseDto(
                guestBook.getId(),
                guestBook.getName(),
                guestBook.getTitle(),
                guestBook.getContent(),
                guestBook.getFile() != null ? guestBook.getFile().getS3Url() : null,
                guestBook.getCreatedAt()
        ));
    }

}
