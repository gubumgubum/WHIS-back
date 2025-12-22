package com.example.idea.alarm.repository;

import com.example.idea.alarm.model.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    // 특정 사용자의 전체 알람 조회
    List<Alarm> findByReceiverIdOrderByCreatedAtDesc(Long receiverId);

    // 읽음 여부에 따른 필터링 조회
    List<Alarm> findByReceiverIdAndIsReadOrderByCreatedAtDesc(Long receiverId, boolean isRead);
}