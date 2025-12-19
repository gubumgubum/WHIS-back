package org.example.whisauth.auth;

import lombok.RequiredArgsConstructor;
import org.example.whisauth.upload.FileService;
import org.example.whisauth.user.User;
import org.example.whisauth.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final FileService fileService;

    public void signup(SignupRequestDto dto, MultipartFile profileImage) {
        String imageUrl = null;
        if (profileImage != null && !profileImage.isEmpty()) {
            imageUrl = fileService.saveProfileImage(profileImage);
        }

        User user = new User(
                dto.getEmail(),
                passwordEncoder.encode(dto.getPassword()),
                dto.getName(),
                dto.getGrade(),
                imageUrl
        );

        userRepository.save(user);
    }

    public String login(LoginRequestDto dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("유저 없음"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호 틀림");
        }

        return jwtProvider.createAccessToken(user.getId());
    }
}
