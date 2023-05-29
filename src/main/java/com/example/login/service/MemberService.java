package com.example.login.service;

import com.example.login.domain.JoinDto;
import com.example.login.domain.Member;
import com.example.login.domain.Role;
import com.example.login.repository.MemberRepository;
import com.example.login.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder encoder;

    public boolean save(JoinDto member) {
        if (duplicatedMember(member)) {
            return false;
        }
        Role role = roleRepository.findByRoleName("ROLE_ADMIN");
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        Member joinMember = Member.builder()
                .userId(member.getUserId())
                .email(member.getEmail())
                .name(member.getName())
                .password(encoder.encode(member.getPassword()))
                .role("ROLE_ADMIN")
                .Roles(roles)
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
