package com.studiosheepdog.dognapper.board.domain.aggregate.entity;

import com.studiosheepdog.dognapper.board.domain.aggregate.enums.BoardCategory;
import com.studiosheepdog.dognapper.commons.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Table(name = "BOARD_TB")
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title; // 제목
    private String content; // 본문

    @Enumerated(EnumType.STRING)
    private BoardCategory category; // 카테고리

    private String writer; // 작성자 닉네임

    @OneToMany(mappedBy = "board", orphanRemoval = true)
    private List<Comment> comments; // 댓글
    private Integer commentCnt;     // 댓글 수

    @OneToMany(mappedBy = "board", orphanRemoval = true)
    private List<Like> likes; // 좋아요
    private Integer likeCnt; // 좋아요 수

}
