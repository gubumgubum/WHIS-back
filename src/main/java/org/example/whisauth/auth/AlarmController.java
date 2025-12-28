package org.example.whisauth.auth;

import lombok.RequiredArgsConstructor;
import org.example.whisauth.user.AlarmResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/alarm")
public class AlarmController {

    private final AlarmService alarmService;

    @GetMapping
    public List<AlarmResponse> getMyAlarms() {
        return alarmService.getMyAlarms();
    }
}

