// 경로: src/main/java/com/example/idea/query/dto/MyCommentResponse.java
package com.example.idea.comment.query.dto;

import java.time.LocalDateTime;

public record MyCommentResponse(
        Long commentId,
        String content,
        LocalDateTime createdAt,
        Long postId,
        String postTitle
) {}