package com.toyproject.ecommerce;

import com.toyproject.ecommerce.entity.Address;
import com.toyproject.ecommerce.entity.Member;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 테스트 할 때마다 회원가입할 필요 없게 디비 상에 회원 정보 보관
 */
@Component
@RequiredArgsConstructor
public class InitDB {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void dbInit1() {
            Member member = Member.builder()
                    .name("심우민")
                    .email("torry2635@gmail.com")
                    .password("123")
                    .address(new Address("Inchoen", "Ieumro", "2340"))
                    .build();
            member.changeRole("admin");

            em.persist(member);
        }

        public void dbInit2() {
            Member member = Member.builder()
                    .name("신예빈")
                    .email("worejj@naver.com")
                    .password("123")
                    .address(new Address("Gimpo", "Ieumro", "1234"))
                    .build();
            member.changeRole("user");

            em.persist(member);
        }
    }
}
