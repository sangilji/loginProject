package com.example.login.controller;

import com.example.login.auth.PrincipalDetails;
import com.example.login.domain.JoinDto;
import com.example.login.domain.LoginForm;
import com.example.login.domain.Member;
import com.example.login.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.security.Principal;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {


    private final MemberService memberService;

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
    public @ResponseBody String admin() {
        return "admin";
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
    public String join(@Valid @ModelAttribute("member") JoinDto member, BindingResult bindingResult ) {
        if (bindingResult.hasErrors()) {
            return "joinForm";
        }
        member.setRole("ROLE_USER");

        boolean saveMember = memberService.save(member);
        if (!saveMember) {
            bindingResult.reject("duplicatedMessage","아이디 또는 이메일이 중복입니다.");
            return "joinForm";
        }

        return "redirect:loginForm";
    }
    @Secured("ROLE_ADMIN")
    @GetMapping("/info")
    public @ResponseBody String info(){
        return "개인정보";
    }

    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
//    @PostAuthorize()
    @GetMapping("/data")
    public @ResponseBody String data(){
        return "여러개";
    }

}
