package com.example.idea.alarm.controller;

import com.example.idea.alarm.model.Alarm;
import com.example.idea.alarm.repository.AlarmRepository;
import com.example.idea.alarm.service.AlarmService; // 👈 서비스 임포트
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/alarm")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AlarmController {

    private final AlarmRepository alarmRepository;
    private final AlarmService alarmService; // 👈 1. 서비스 주입 (final로 선언하면 자동 주입됨)

    // 1. 전체 알림 조회
    @GetMapping("/all")
    public List<Alarm> getAllAlarms(@RequestParam Long userId) {
        return alarmRepository.findByReceiverIdOrderByCreatedAtDesc(userId);
    }

    // 2. 읽음/안읽음 필터링
    @GetMapping("/filter")
    public List<Alarm> getFilteredAlarms(@RequestParam Long userId, @RequestParam boolean isRead) {
        return alarmRepository.findByReceiverIdAndIsReadOrderByCreatedAtDesc(userId, isRead);
    }

    // 3. [신규] 신고 당할 시 알림: POST /alarm/get-report
    // 명세서 상의 '진행 중'인 기능 구현
    @PostMapping("/get-report")
    public String sendReportWarning(@RequestParam Long reportedUserId,
                                    @RequestParam String contentType) {
        alarmService.sendReportWarning(reportedUserId, contentType);
        return "피신고자에게 경고 알림이 발송되었습니다.";
    }

    // 4. [신규] 신고 접수 진행 상황 알림: POST /alarm/reporting
    // 명세서 상의 '진행 중'인 기능 구현
    @PostMapping("/reporting")
    public String sendReportProgress(@RequestParam Long reporterId,
                                     @RequestParam String status) {
        alarmService.sendReportProgress(reporterId, status);
        return "신고자에게 진행 상황 알림이 발송되었습니다.";
    }
}