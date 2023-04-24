package com.example.login.repository;

import com.example.login.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MemberRepository extends JpaRepository<Member,Integer> {
    Member findByUserId(String userId);

    boolean existsByUserId(String userId);

    boolean existsByEmail(String email);
}
