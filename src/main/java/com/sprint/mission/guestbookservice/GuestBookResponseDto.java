package com.sprint.mission.guestbookservice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GuestBookResponseDto {
    private Long id;
    private String name;
    private String title;
    private String content;
    private String imageUrl;
    private LocalDateTime createdAt;
}
