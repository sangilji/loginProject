package com.example.login.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
public class Member {


    @Id @GeneratedValue
    private int id;

    @NotNull
    private String userId;

    @NotNull
    private String password;

    @NotNull
    private String email;

    @NotNull
    private String name;
}
