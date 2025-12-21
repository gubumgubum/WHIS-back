// MyCommentResponse.java
package com.example.idea.comment.dto;

import java.time.LocalDateTime;

public record MyCommentResponse(
        Long commentId,      // 댓글 고유 번호
        String content,      // 댓글 내용
        LocalDateTime createdAt, // 작성일
        Long postId,         // 해당 게시글 ID
        String postTitle     // 해당 게시글 제목 (조회 시 핵심!)
) {}