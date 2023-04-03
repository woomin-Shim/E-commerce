package com.toyproject.ecommerce.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart {

    @Id
    @GeneratedValue
    @Column(name = "cart_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)  //모든 ~ToOne 관계는 LAZY로딩으로 설정(EAGER 쓰지 말기!!)
    @JoinColumn(name = "member_id")
    private Member member;

    private Cart(Member member) {
        this.member = member;
    }

    public static Cart createCart(Member member) {
        return new Cart(member);
    }
}
