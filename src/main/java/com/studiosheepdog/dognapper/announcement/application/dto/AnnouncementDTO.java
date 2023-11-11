package com.studiosheepdog.dognapper.announcement.application.dto;

import com.studiosheepdog.dognapper.announcement.domain.aggregate.entity.Announcement;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class AnnouncementDTO {
    private Long id; //고유 아아디

    private String title; //제목

    private String content; //내용

    private String writer; //작성자

    private LocalDateTime createdDate; // 작성일 추가
    private LocalDateTime modifiedDate; // 수정일 추가

    public AnnouncementDTO() {
    }

    // entity -> DTO 변환
    public AnnouncementDTO(Announcement announcement) {
        this.id = announcement.getId();
        this.title = announcement.getTitle();
        this.content = announcement.getContent();
        this.writer = announcement.getWriter();
        this.createdDate = announcement.getCreatedDate(); // 작성일 설정
        this.modifiedDate = announcement.getModifiedDate(); // 수정일 설정
    }
}
