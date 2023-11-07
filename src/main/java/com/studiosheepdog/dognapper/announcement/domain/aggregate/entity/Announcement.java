package com.studiosheepdog.dognapper.announcement.domain.aggregate.entity;

import com.studiosheepdog.dognapper.commons.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "ANNOUNCEMENT_TB")
public class Announcement extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //고유 아아디

    private String title; //제목

    private String content; //내용

    private String writer; //작성자

}
