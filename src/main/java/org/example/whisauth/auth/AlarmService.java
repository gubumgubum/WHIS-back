package org.example.whisauth.auth;

import lombok.RequiredArgsConstructor;
import org.example.whisauth.user.AlarmRepository;
import org.example.whisauth.user.AlarmResponse;
import org.example.whisauth.user.User;
import org.example.whisauth.user.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlarmService {

    private final AlarmRepository alarmRepository;
    private final UserRepository userRepository;

    public List<AlarmResponse> getMyAlarms() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId;

        // 1. principal이 User 객체 자체일 경우 (현재 에러 원인)
        if (principal instanceof User) {
            userId = ((User) principal).getId();
        }
        // 2. principal이 ID 문자열일 경우
        else {
            try {
                userId = Long.parseLong(principal.toString());
            } catch (NumberFormatException e) {
                throw new RuntimeException("인증 정보에서 유저 ID를 찾을 수 없습니다: " + principal);
            }
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("해당 유저를 찾을 수 없습니다."));

        return alarmRepository.findByUser(user)
                .stream()
                .map(AlarmResponse::from)
                .toList();
    }
}