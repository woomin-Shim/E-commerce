package com.toyproject.ecommerce.service;

import com.toyproject.ecommerce.entity.Member;
import com.toyproject.ecommerce.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)  //조회하는 곳에서 성능 최적화
public class MemberService {

    private final MemberRepository memberRepository;


    //회원가입
    @Transactional(readOnly = false)
    public Long join(Member member) {
        validateDuplicateMember(member);
        Member savedMember = memberRepository.save(member);
        return savedMember.getId();
    }

    //중복 회원 검증
    private void validateDuplicateMember(Member member) {
        Member findMember = memberRepository.findByEmail(member.getEmail()).orElse(null);
        if (findMember != null) {
            throw new IllegalStateException("이미 존재하는 회원입니다");
        }
    }

    //로그인 체크(null 이면 로그인 실패)
    public Member login(String email, String password) {
        return memberRepository.findByEmail(email)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }

    public Member findMember(Long id) {
        return memberRepository.findById(id).orElseGet(() -> null);
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }
}
