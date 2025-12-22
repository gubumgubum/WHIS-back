package org.example.whisauth.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.whisauth.user.Alarm;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class AlarmResponse {
    private Long id;
    private String message;
    private boolean isRead;
    private LocalDateTime createdAt;

    public static AlarmResponse from(Alarm alarm) {
        return new AlarmResponse(
                alarm.getId(),
                alarm.getMessage(),
                alarm.isRead(),
                alarm.getCreatedAt()
        );
    }
}

