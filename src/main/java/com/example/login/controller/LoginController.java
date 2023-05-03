package com.example.login.controller;

import com.example.login.auth.PrincipalDetails;
import com.example.login.domain.JoinDto;
import com.example.login.domain.LoginRequest;
import com.example.login.domain.Member;
import com.example.login.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Slf4j
@RestController
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
    public String admin() {
        return "admin";
    }

    @GetMapping("/manager")
    public String manager() {
        return "manager";
    }



    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody JoinDto member ) {

        boolean saveMember = memberService.save(member);
        if (!saveMember) {
            return new ResponseEntity<>("중복된 아이디, 이메일", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("완료", HttpStatus.OK);
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
