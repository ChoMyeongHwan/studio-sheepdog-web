package com.studiosheepdog.dognapper.board.domain.aggregate.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "LIKE_TB")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId; // 좋아요를 누른 사용자의 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board; // 좋아요를 누른 게시글

    public Like(String userId, Board board) {
        this.userId = userId;
        this.board = board;
    }
}
