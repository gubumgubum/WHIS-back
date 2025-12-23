package org.example.postlike.controller;

import org.example.postlike.dto.LikeDto;
import org.example.postlike.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    // 1. 좋아요 (POST /like)
    @PostMapping("/like")
    public ResponseEntity<String> likePost(@RequestBody LikeDto.Request request) {
        return ResponseEntity.ok(likeService.toggleLike(request));
    }

    // 2. 좋아요 수 조회 (GET /like/check)
    @GetMapping("/like/check")
    public ResponseEntity<Long> checkLikeCount(@RequestParam Long postId) {
        return ResponseEntity.ok(likeService.getLikeCount(postId));
    }

    // 3. 좋아요 한 글 조회 (GET /like/check-post)
    @GetMapping("/like/check-post")
    public ResponseEntity<List<Long>> checkLikedPosts(@RequestParam Long userId) {
        return ResponseEntity.ok(likeService.getLikedPosts(userId));
    }

    // 4. 좋아요 알림 (POST /like/alarm)
    @PostMapping("/like/alarm")
    public ResponseEntity<String> sendAlarm(@RequestBody LikeDto.AlarmRequest request) {
        likeService.sendLikeAlarm(request);
        return ResponseEntity.ok("알림 전송 요청 완료");
    }
}
