package org.example.whisauth.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public void signup(@RequestBody SignupRequestDto dto) {
        authService.signup(dto);
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequestDto dto) {
        String accessToken = authService.login(dto);
        return Map.of("accessToken", accessToken);
    }
}

