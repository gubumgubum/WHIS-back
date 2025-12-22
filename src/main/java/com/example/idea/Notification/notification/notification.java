package com.example.idea.Notification.notification;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class notification {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long receiverId; // 알림을 받을 사람 (게시글 작성자)
    private String message;  // "새 댓글이 달렸습니다!" 등의 메시지
    private boolean isRead = false; // 읽었는지 여부
    private LocalDateTime createdAt = LocalDateTime.now();
}