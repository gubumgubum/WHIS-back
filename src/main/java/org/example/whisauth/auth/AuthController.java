package org.example.whisauth.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        authService.logout();
        return ResponseEntity.ok().build();
    }
    @Configuration
    public class WebConfig implements WebMvcConfigurer {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")
                    .allowedOrigins("http://localhost:3000")
                    .allowedMethods("GET", "POST", "OPTIONS")
                    .allowCredentials(true);
        }
    }

}


