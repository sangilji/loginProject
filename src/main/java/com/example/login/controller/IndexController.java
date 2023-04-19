package com.example.login.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class IndexController {

    @GetMapping("/")
    public String index(){
        return "home";
    }

    @GetMapping("/")
    public String admin(){
        return "home";
    }

    @GetMapping("/")
    public String manager(){
        return "home";
    }

    @GetMapping("/")
    public String login(){
        return "home";
    }

    @GetMapping("/")
    public String join(){
        return "home";
    }

    @GetMapping("/joinProcess")
    public String joinProcess(){
        return "redirect:home";
    }

}
