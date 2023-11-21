package com.studiosheepdog.dognapper.board.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WriteCommentDTO {
    private String content;
    private String writer;
}
