package com.toyproject.ecommerce.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // new 생성자 생성 못하게 -> Member member = new Member()
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String name;

    private String email;
    private String password;

    @Embedded
    private Address address;

    @Enumerated(value = EnumType.STRING)
    private Role role;


    @Builder
    private Member(String name, Address address, String email, String password) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.password = password;
    }

    public void changeRole(String role) {
        if (role.equals("admin")) {
            this.role = Role.ADMIN;
        } else {
            this.role = Role.USER;
        }
    }

}
