package com.studiosheepdog.dognapper.board.domain.aggregate.entity;

import com.studiosheepdog.dognapper.commons.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "COMMENT_TB")
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content; // 댓글 내용
    private String writer; // 댓글 작성자 닉네임

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board; // 댓글이 달린 게시글

    public Comment(String content, String writer, Board board) {
        this.content = content;
        this.writer = writer;
        this.board = board;
    }
}
