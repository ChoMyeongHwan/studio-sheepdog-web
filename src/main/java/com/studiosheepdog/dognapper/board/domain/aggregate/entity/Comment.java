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

    private String body;

    private String writer; // 댓글 작성자 닉네임

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board; // 댓글이 달린 게시판

    public Comment(String body, String writer, Board board) {
        this.body = body;
        this.writer = writer;
        this.board = board;
    }

    public void update(String newBody) {
        this.body = newBody;
    }
}
