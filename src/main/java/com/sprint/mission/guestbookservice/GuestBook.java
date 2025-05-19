package com.sprint.mission.guestbookservice;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class GuestBook {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String title;

    private String content;

    @CreatedDate
    private LocalDateTime createdAt;

    @OneToOne
    @JoinColumn(name = "file_id")
    private FileEntity file;

    public GuestBook(String name, String title, String content, FileEntity file) {
        this.name = name;
        this.title = title;
        this.content = content;
        this.file = file;
    }
}
