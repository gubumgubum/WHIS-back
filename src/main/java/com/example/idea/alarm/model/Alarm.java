package com.example.idea.alarm.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Alarm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long receiverId;    // 알람을 받을 사용자 ID
    private String message;     // 알람 내용
    private String type;        // 알람 유형 (REPORT, STATUS 등)
    private boolean isRead = false; // 읽음 여부 (필터링용)
    private LocalDateTime createdAt = LocalDateTime.now();
}