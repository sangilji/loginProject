package com.example.login.controller;

import com.example.login.auth.PrincipalDetails;
import com.example.login.domain.LoginForm;
import com.example.login.domain.Member;
import com.example.login.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Slf4j
@Controller
@RequiredArgsConstructor
public class IndexController {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;

    //    @GetMapping("/")
    public String homeV1(Principal principal, Model model) {
        if (principal == null) {
            return "home";
        }
        model.addAttribute("name", principal.getName());
        return "loginHome";
    }

    //    @GetMapping("/")
    public String homeV2(Authentication authentication, Model model) {
        if (authentication == null) {
            return "home";
        }
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        model.addAttribute("name", principal.getUsername());
        return "loginHome";
    }

    @GetMapping("/")
    public String homeV3(@AuthenticationPrincipal PrincipalDetails principal, Model model) {
        if (principal == null) {
            return "home";
        }
        model.addAttribute("name", principal.getUsername());
        return "loginHome";
    }

    @GetMapping("/admin")
    public String admin() {
        return "home";
    }

    @GetMapping("/manager")
    public String manager() {
        return "home";
    }

    @GetMapping("/loginForm")
    public String login(@ModelAttribute("loginForm") LoginForm loginForm) {
        return "loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm(@ModelAttribute("member") Member member) {
        return "/joinForm";
    }

    @PostMapping("/join")
    public String join(@Validated @ModelAttribute("member") Member member, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "joinForm";
        }
        member.setRole("ROLE_USER");
        String rawPassword = member.getPassword();
        String encodePassword = encoder.encode(rawPassword);
        member.setPassword(encodePassword);
        memberRepository.save(member);
        return "redirect:loginForm";
    }

}
