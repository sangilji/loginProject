package com.example.login.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
public class Member {


    @Id @GeneratedValue
    private int id;

    @NotNull
    @NotEmpty
    private String userId;

    @NotNull
    @NotEmpty
    private String password;

    @NotNull
    @NotEmpty
    private String email;

    @NotNull
    @NotEmpty
    private String name;

    private String role;
}
