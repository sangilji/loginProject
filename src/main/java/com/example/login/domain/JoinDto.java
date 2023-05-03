package com.example.login.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter @Setter
public class JoinDto {


//    @NotEmpty
//    @Length(min = 8, max = 12)
    private String userId;

//    @NotEmpty
//    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$", message = "비밀번호 형식")
    private String password;

//    @NotEmpty
//    @Email
    private String email;

//    @NotEmpty
//    @Pattern(regexp = "^[가-힣]{3,5}$")
    private String name;

    private String role;
}