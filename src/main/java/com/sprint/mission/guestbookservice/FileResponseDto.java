package com.sprint.mission.guestbookservice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileResponseDto {
    private Long id;
    private String fileName;
    private String description;
    private String contentType;
    private String url;
    private Long size;
}
