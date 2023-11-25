package com.studiosheepdog.dognapper.member.domain.aggregate.entity;

import com.studiosheepdog.dognapper.member.domain.aggregate.enums.Role;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Table(name = "MEMBER_TB")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false) // 이메일의 유일성을 데이터베이스 레벨에서 강제화
    private String email;

    private String encryptedPassword; // 비밀번호는 단방향 암호화하여 저장

    @Column(nullable = false)
    private String name;

    private String googleId;

    @Enumerated(EnumType.STRING)
    private Role role; // USER, ADMIN

    public void update(String name, String encryptedPassword) {
        this.name = name;
        this.encryptedPassword = encryptedPassword;
    }
}
