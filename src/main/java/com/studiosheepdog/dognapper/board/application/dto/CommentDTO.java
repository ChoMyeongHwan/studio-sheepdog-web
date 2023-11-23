package com.studiosheepdog.dognapper.board.application.dto;

import com.studiosheepdog.dognapper.board.domain.aggregate.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CommentDTO {
    private Long id;
    private String writer;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    // Comment to CommentDTO 변환 메서드
    public static CommentDTO from(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setWriter(comment.getWriter());
        commentDTO.setContent(comment.getContent());
        commentDTO.setCreatedDate(comment.getCreatedDate());
        commentDTO.setModifiedDate(comment.getModifiedDate());
        return commentDTO;
    }
}
