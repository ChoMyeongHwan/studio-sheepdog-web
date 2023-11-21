package com.studiosheepdog.dognapper.board.application.dto;

import com.studiosheepdog.dognapper.board.domain.aggregate.enums.BoardCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WriteBoardDTO {
    private String title;
    private String content;
    private BoardCategory category;
    private String writer;
}