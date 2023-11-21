package com.studiosheepdog.dognapper.board.application.dto;

import com.studiosheepdog.dognapper.board.domain.aggregate.entity.Board;
import com.studiosheepdog.dognapper.board.domain.aggregate.enums.BoardCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

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

    /*
    * Entity -> DTO
    * 해당 메서드가 BoardDTO 인스턴스를 생성하고 초기화하는 역할을 함
    * static 메서드는 인스턴스 생성 없이 클래스 이름으로 직접 호출할 수 있어서 편리하게 사용
    * */
    public static BoardDTO from(Board board) {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setId(board.getId());
        boardDTO.setTitle(board.getTitle());
        boardDTO.setContent(board.getContent());
        boardDTO.setCategory(board.getCategory());
        boardDTO.setWriter(board.getWriter());
        boardDTO.setCommentCount(board.getCommentCount()); // 댓글 수
        boardDTO.setLikeCount(board.getLikeCount()); // 좋아요 수
        boardDTO.setCreatedDate(board.getCreatedDate()); // 작성일
        return boardDTO;
    }
}
