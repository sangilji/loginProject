package com.example.login.controller;

import com.example.Status.*;
import com.example.login.auth.PrincipalDetails;
import com.example.login.domain.JoinDto;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> homeV2(Authentication authentication, Model model) {
        if (authentication == null) {
            return ResponseEntity.ok()
                    .body(JsonResponse.ok(true,ResponseMessage.NOT_INFORMATION.getMessage()));
        }
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        model.addAttribute("name", principal.getUsername());
        return ResponseEntity.ok()
                .body(JsonResponse.ok(true,ResponseMessage.LOGIN_SUCCESS.getMessage(),principal));

    }

    @GetMapping("/")
    public ResponseEntity<?> homeV3(@AuthenticationPrincipal PrincipalDetails principal) {
        if (principal == null) {
            return ResponseEntity.ok()
                    .body(JsonResponse.ok(true,ResponseMessage.NOT_INFORMATION.getMessage()));
        }
        return ResponseEntity.ok()
                .body(JsonResponse.ok(true,ResponseMessage.LOGIN_SUCCESS.getMessage(),principal));
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
            return new ResponseEntity<>(JsonResponse.fail(false, ResponseMessage.LOGIN_FAIL.getMessage(), ErrorCode.E00.getErrorCode()), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(JsonResponse.ok(true,ResponseMessage.LOGIN_SUCCESS.getMessage(),member), HttpStatus.OK);
    }
//    @Secured("ROLE_ADMIN")
//    @GetMapping("/info")
//    public @ResponseBody String info(){
//        return "개인정보";
//    }
//
//    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
////    @PostAuthorize()
//    @GetMapping("/data")
//    public @ResponseBody String data(){
//        return "여러개";
//    }

}
