package org.example.whisauth.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Email;
import lombok.Getter;

@Getter
public class SignupRequestDto {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
            message = "비밀번호는 영문과 숫자를 포함한 8자 이상이어야 합니다."
    )
    private String password;

    @NotBlank
    private String name;

    private Integer grade;



}