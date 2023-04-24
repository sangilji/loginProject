package com.example.login.service;

import com.example.login.domain.Member;
import com.example.login.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;

    public boolean save(Member member) {
        if (duplicatedMember(member)) {
            return false;
        }

        member.setPassword(encoder.encode(member.getPassword()));
        memberRepository.save(member);
        return true;
    }

    private boolean duplicatedMember(Member member) {
        if (existsUserId(member.getUserId())) {
            return true;
        }
        if (existsEmail(member.getEmail())) {
            return true;
        }
        return false;
    }

    private boolean existsUserId(String userId) {
        return memberRepository.existsByUserId(userId);
    }

    private boolean existsEmail(String email) {
        return memberRepository.existsByEmail(email);
    }
}
