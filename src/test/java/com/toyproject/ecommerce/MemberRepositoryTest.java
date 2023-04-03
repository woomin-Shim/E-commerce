package com.toyproject.ecommerce;

import com.toyproject.ecommerce.domain.Member;
import com.toyproject.ecommerce.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional
    @Rollback(false)
    public void testMember() {

        Member member = Member.builder()
                .name("memberA")
                .build();
        Member savedMember = memberRepository.save(member);

        Member findMember = memberRepository.findById(member.getId()).get();



        Assertions.assertThat(findMember.getId()).isEqualTo(savedMember.getId());
        Assertions.assertThat(findMember.getName()).isEqualTo(member.getName());

    }
}
