package com.toyproject.ecommerce.service;


import com.toyproject.ecommerce.domain.Address;
import com.toyproject.ecommerce.domain.Member;
import com.toyproject.ecommerce.domain.Role;
import com.toyproject.ecommerce.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberService memberService;

    private Member createMember() {
        return Member.builder()
                .name("woomin")
                .address(new Address("Incheon", "Ieum-ro", "123"))
                .email("woomin@google.com")
                .password("123")
                .build();
    }

    @Test
    @DisplayName("회원가입 테스트")
    public void join() {
        //given
        Member member = this.createMember();

        //when
        Long savedId = memberService.join(member);

        //then
        Assert.assertEquals(member, memberService.findMember(savedId));
    }

    @Test
    @DisplayName("중복회원 검증 테스트")
    public void duplicateMember() {
        //given
        Member member = this.createMember();

        //when
        memberService.join(member);
        try {
            memberService.join(member); //예외가 발생해야 함
        } catch (IllegalStateException e) {
            return;
        }

        //then
        Assert.fail("예외가 발생해야 함!!");
    }

    @Test
    @DisplayName("관리자, 사용자 역할 테스트")
    public void setRole() {
        //given
        Member member = createMember();
        member.changeRole("admin");  //관리자(판매자)

        //when
        Long savedId = memberService.join(member);

        //then
        Assert.assertEquals(member.getRole(), memberService.findMember(savedId).getRole());
    }

    @Test
    @DisplayName("로그인 테스트")
    public void login() {
        //given
        Member member = createMember();
        Long savedId = memberService.join(member);

        //when
        Member loginMember = memberService.login("woomin@google.com", "123");

        //then
        Assert.assertEquals(member.getId(), loginMember.getId());
    }
}
