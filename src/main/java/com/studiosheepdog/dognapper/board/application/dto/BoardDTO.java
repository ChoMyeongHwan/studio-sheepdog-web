package com.studiosheepdog.dognapper.board.application.dto;

import com.studiosheepdog.dognapper.board.domain.aggregate.entity.Board;
import com.studiosheepdog.dognapper.board.domain.aggregate.enums.BoardCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class BoardDTO {
    private Long id;
    private String title;
    private String content;
    private BoardCategory category;
    private String writer;
    private int commentCount; // 댓글 수
    private int likeCount; // 좋아요 수
    private LocalDateTime createdDate; // 작성일
    private LocalDateTime modifiedDate; // 수정일

    // CommentDTO 리스트 필드 추가
    private List<CommentDTO> comments;
    // LikeDTO 리스트 필드 추가
    private List<LikeDTO> likes;

    public static BoardDTO from(Board board) {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setId(board.getId());
        boardDTO.setTitle(board.getTitle());
        boardDTO.setContent(board.getContent());
        boardDTO.setCategory(board.getCategory());
        boardDTO.setWriter(board.getWriter());
        boardDTO.setCommentCount(board.getCommentCount());
        boardDTO.setLikeCount(board.getLikeCount());
        boardDTO.setCreatedDate(board.getCreatedDate());
        boardDTO.setModifiedDate(board.getModifiedDate());

        // 댓글들도 CommentDTO로 변환하여 설정
        if (board.getComments() != null) {
            boardDTO.setComments(board.getComments().stream()
                    .map(CommentDTO::from)
                    .collect(Collectors.toList()));
        }

        // 좋아요들도 LikeDTO로 변환하여 설정
        if (board.getLikes() != null) {
            boardDTO.setLikes(board.getLikes().stream()
                    .map(LikeDTO::from)
                    .collect(Collectors.toList()));
        }

        return boardDTO;
    }
}
