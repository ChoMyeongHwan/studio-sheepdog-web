package com.studiosheepdog.dognapper.announcement.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnnouncementDTO {
    private Long id; //고유 아아디

    private String title; //제목

    private String content; //내용

    private String writer; //작성자
}
