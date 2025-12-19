package org.example.whisauth.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public void signup(
            @RequestPart SignupRequestDto dto,
            @RequestPart(required = false) MultipartFile profileImage
    ) {
        authService.signup(dto, profileImage);
    }

    @PostMapping("/signin")
    public Map<String, String> login(@RequestBody LoginRequestDto dto) {
        String accessToken = authService.login(dto);
        return Map.of("accessToken", accessToken);
    }
}

