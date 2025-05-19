package com.sprint.mission.guestbookservice;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/guestbooks")
@RequiredArgsConstructor
public class GuestbookController {
    private final GuestBookService guestBookService;

    // 방명록 작성
    @PostMapping
    public ResponseEntity<GuestBookResponseDto> createGuestbook(
            @RequestPart("name") String name,
            @RequestPart("title") String title,
            @RequestPart("content") String content,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        return ResponseEntity.ok(guestBookService.createGuestbook(name, title, content, image));
    }

    // 방명록 리스트 조회
    @GetMapping
    public ResponseEntity<Page<GuestBookResponseDto>> getGuestbookList(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size
    ) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return ResponseEntity.ok(guestBookService.getGuestbookList(pageRequest));
    }

    // 방명록 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<GuestBookResponseDto> getGuestbook(@PathVariable Long id) {
        return ResponseEntity.ok(guestBookService.getGuestbook(id));
    }
}
