package com.example.idea.alarm.controller;

import com.example.idea.alarm.model.Alarm;
import com.example.idea.alarm.repository.AlarmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/alarm") //
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // 프론트엔드 연결 허용
public class AlarmController {

    private final AlarmRepository alarmRepository;

    // 전체 알림 조회: GET /alarm/all
    @GetMapping("/all")
    public List<Alarm> getAllAlarms(@RequestParam Long userId) {
        return alarmRepository.findByReceiverIdOrderByCreatedAtDesc(userId);
    }

    // 읽음/안읽음 필터링: GET /alarm/filter
    @GetMapping("/filter")
    public List<Alarm> getFilteredAlarms(@RequestParam Long userId, @RequestParam boolean isRead) {
        return alarmRepository.findByReceiverIdAndIsReadOrderByCreatedAtDesc(userId, isRead);
    }
}