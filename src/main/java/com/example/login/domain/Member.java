package com.example.login.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Member implements Serializable {


    @Id @GeneratedValue
    private int id;

    @NotNull
    @Column(unique = true)
    private String userId;

    @NotNull
    private String password;

    @NotNull
    @Column(unique = true)
    private String email;

    @NotNull
    private String name;

    @NotNull
    private String role;

    @ManyToMany
    @JoinTable(name = "member_roles", joinColumns = {@JoinColumn(name = "member_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> Roles = new HashSet<>();
}
