package org.example.whisauth.auth;

import lombok.Getter;

@Getter
public class SignupRequestDto {
    private String email;
    private String password;
    private String name;
    private Integer grade;
}