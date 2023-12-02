package com.studiosheepdog.dognapper.member.application.service;

import com.studiosheepdog.dognapper.member.application.dto.MemberDTO;
import com.studiosheepdog.dognapper.member.domain.aggregate.entity.Member;
import com.studiosheepdog.dognapper.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원 가입
    public Member register(MemberDTO memberDTO) {
        Member member = Member.builder()
                .email(memberDTO.getEmail())
                .encryptedPassword(passwordEncoder.encode(memberDTO.getPassword()))
                .name(memberDTO.getName())
                .build();
        return memberRepository.save(member);
    }

    // 로그인
    public Optional<Member> login(String email, String password) {
        Optional<Member> member = memberRepository.findByEmail(email);
        if (member.isPresent() && passwordEncoder.matches(password, member.get().getEncryptedPassword())) {
            return member;
        }
        return Optional.empty();
    }

    // 구글 ID를 통해 멤버를 조회(구글 로그인 시 사용)
    @Transactional(readOnly = true)
    public Optional<Member> findByGoogleId(String googleId) {
        return memberRepository.findByGoogleId(googleId);
    }

    // 이메일을 통해 멤버를 조회(자체 로그인 시 사용)
    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    // 멤버 ID를 통해 멤버 정보를 조회
    public Optional<Member> getMember(Long id) {
        return memberRepository.findById(id);
    }

    // 멤버 ID와 MemberDTO를 받아 해당 멤버의 정보를 업데이트
    public Member updateMember(Long id, MemberDTO memberDTO) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Member not found"));
        member.update(memberDTO.getName(), passwordEncoder.encode(memberDTO.getPassword()));
        return memberRepository.save(member);
    }

    // 멤버 ID를 받아 해당 멤버를 삭제
    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }
}
