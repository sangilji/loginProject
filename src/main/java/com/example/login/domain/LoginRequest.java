package com.example.login.domain;

import lombok.Data;

@Data
public class LoginRequest {

    private String userId;
    private String password;
}