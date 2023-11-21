package com.studiosheepdog.dognapper.board.domain.aggregate.entity;

import com.studiosheepdog.dognapper.board.domain.aggregate.enums.BoardCategory;
import com.studiosheepdog.dognapper.commons.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
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

    /* 댓글이나 좋아요가 아직 없는 상태를 null이 아닌 빈 리스트로 표현 */
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>(); // 댓글

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes = new ArrayList<>(); // 좋아요


    public int getCommentCount() {
        return comments.size();
    }

    public int getLikeCount() {
        return likes.size();
    }
}
