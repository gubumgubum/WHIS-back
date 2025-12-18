package org.example.whisauth.auth;

import lombok.RequiredArgsConstructor;
import org.example.whisauth.user.User;
import org.example.whisauth.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    private static final String SCHOOL_EMAIL_DOMAIN = "@gsm.hs.kr";

    private void validateEmail(String email) {
        if (email == null || !email.endsWith(SCHOOL_EMAIL_DOMAIN)) {
            throw new RuntimeException("학교 이메일(@gsm.hs.kr)만 사용 가능합니다.");
        }
    }

    // 회원가입
    public void signup(SignupRequestDto dto) {
        validateEmail(dto.getEmail());

        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("이미 가입된 이메일입니다.");
        }

        User user = new User(
                dto.getEmail(),
                passwordEncoder.encode(dto.getPassword()),
                dto.getName()
        );

        userRepository.save(user);
    }

    // 로그인
    public String login(LoginRequestDto dto) {
        validateEmail(dto.getEmail());

        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("비밀번호 불일치");
        }

        return jwtProvider.createAccessToken(user.getId());
    }
}
