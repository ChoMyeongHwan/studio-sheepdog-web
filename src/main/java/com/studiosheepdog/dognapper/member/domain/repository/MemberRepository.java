package com.studiosheepdog.dognapper.member.domain.repository;

import com.studiosheepdog.dognapper.member.domain.aggregate.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    Optional<Member> findByGoogleId(String googleId);
}
