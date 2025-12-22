package com.example.idea.Notification.notification;

import com.example.idea.Notification.notification.NotificationRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificationRepository extends JpaRepository<notification, Long> {
    // 읽지 않은 알림만 가져오는 기능
    List<notification> findByReceiverIdAndIsReadFalse(Long receiverId);
}