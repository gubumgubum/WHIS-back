package org.example.whisauth.auth;

import lombok.Getter;

@Getter
public class LoginRequestDto {
    private String email;
    private String password;
    private String name;
    private Integer grade;

}

