package org.example.whisauth.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.whisauth.user.Alarm;

import java.time.LocalDateTime;

@Getter
@Builder
@Setter
public class AlarmResponse {

    private String message;
    private boolean read;
    private LocalDateTime createdAt;

    public static AlarmResponse from(Alarm alarm) {
        return AlarmResponse.builder()
                .message(alarm.getMessage())
                .read(alarm.isRead())
                .createdAt(alarm.getCreatedAt())
                .build();
    }
}

