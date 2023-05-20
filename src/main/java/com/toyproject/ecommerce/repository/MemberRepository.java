package com.toyproject.ecommerce.repository;

import com.toyproject.ecommerce.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {


    List<Member> findByName(String name);

    List<Member> findAllByEmail(String email);
    Optional<Member> findByEmail(String email);
}
