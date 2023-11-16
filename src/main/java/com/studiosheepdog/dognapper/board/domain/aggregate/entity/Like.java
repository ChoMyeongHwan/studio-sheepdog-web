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

    private Long memberId; // 좋아요를 누른 회원

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board; // 좋아요가 추가된 게시글

}
