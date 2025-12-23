package org.example.postlike.dto;

import lombok.Data;

public class LikeDto {

    @Data
    public static class Request {
        private Long userId;
        private Long postId;
    }

    @Data
    public static class AlarmRequest {
        private Long targetUserId;
        private String message;
    }
}