package org.example.whisauth.auth;

import lombok.RequiredArgsConstructor;
import org.example.whisauth.user.Alarm;
import org.example.whisauth.user.AlarmRepository;
import org.example.whisauth.user.User;
import org.example.whisauth.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final AlarmRepository alarmRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Transactional
    public void signup(SignupRequestDto request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("이미 존재하는 이메일");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .grade(request.getGrade())
                .build();

        userRepository.save(user);

        alarmRepository.save(
                Alarm.builder()
                        .user(user)
                        .message("회원가입을 성공하였습니다.")
                        .isRead(false)
                        .createdAt(LocalDateTime.now())
                        .build()
        );
    }

    public String login(LoginRequestDto dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("계정이 존재하지 않거나 비밀번호가 틀렸습니다"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 다릅니다");
        }

        return jwtProvider.createAccessToken(user.getId());
    }

    public void logout() {
    }
}
