package com.studiosheepdog.dognapper.announcement.domain.aggregate.entity;

import com.studiosheepdog.dognapper.commons.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "ANNOUNCEMENT_TB")
public class Announcement extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //고유 아아디

    @Column(nullable = false)
    private String title; //제목

    @Column(nullable = false)
    private String content; //내용

    private String writer; //작성자

    @Builder
    public Announcement(String title, String content, String writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
    }

    public void update(String title, String content, String writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
    }

}