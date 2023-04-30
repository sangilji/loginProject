package com.example.login.service;

import com.example.login.domain.JoinDto;
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

    public boolean save(JoinDto member) {
        if (duplicatedMember(member)) {
            return false;
        }

        Member joinMember = Member.builder()
                .userId(member.getUserId())
                .email(member.getEmail())
                .name(member.getName())
                .password(encoder.encode(member.getPassword()))
                .role(member.getRole())
                .build();

        memberRepository.save(joinMember);
        return true;
    }

    private boolean duplicatedMember(JoinDto member) {
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
