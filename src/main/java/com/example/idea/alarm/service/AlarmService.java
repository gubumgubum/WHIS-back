package com.example.idea.alarm.service; // 본인의 패키지 경로에 맞게 수정

import com.example.idea.alarm.model.Alarm; // 👈 1. Alarm 엔티티 임포트 확인
import com.example.idea.alarm.repository.AlarmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service // 👈 2. 이 어노테이션이 있어야 주입이 가능합니다.
public class AlarmService {

    @Autowired
    private AlarmRepository alarmRepository;

    // 신고자용 알림
    public void sendReportConfirmation(Long reporterId, String reason) {
        Alarm alarm = new Alarm();
        alarm.setReceiverId(reporterId); // 👈 3. Alarm 엔티티에 이 메서드가 있는지 확인
        alarm.setMessage("신고가 접수되었습니다: " + reason);
        alarm.setType("REPORT_SUBMITTED");
        alarmRepository.save(alarm);
    }

    // 피신고자용 경고 알림 (신고 당할 시 알림)
    public void sendReportWarning(Long reportedUserId, String contentType) {
        Alarm alarm = new Alarm();
        alarm.setReceiverId(reportedUserId);
        alarm.setMessage("회원님의 " + contentType + "이(가) 신고되었습니다.");
        alarm.setType("REPORTED_WARNING");
        alarmRepository.save(alarm);
    }

    // 3. 신고 접수 진행 상황 알림 (신고자에게 발송)
    public void sendReportProgress(Long reporterId, String status) {
        Alarm alarm = new Alarm();
        alarm.setReceiverId(reporterId); // 신고를 한 사람(신고자)이 알림을 받음
        alarm.setMessage("신고하신 내역의 처리 상태가 [" + status + "](으)로 업데이트되었습니다.");
        alarm.setType("REPORT_PROGRESS"); // 알림 타입 설정
        alarmRepository.save(alarm); // DB에 저장
    }
}