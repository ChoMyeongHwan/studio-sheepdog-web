package com.studiosheepdog.dognapper.board.application.dto;

import com.studiosheepdog.dognapper.board.domain.aggregate.enums.BoardCategory;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class WriteBoardDTO {

    private String title; // 제목
    private String content; // 본문

    private BoardCategory category; // 카테고리

    private String writer; // 작성자 닉네임

    public WriteBoardDTO() {
    }

    public WriteBoardDTO(String title, String content, BoardCategory category, String writer) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.writer = writer;
    }
}
