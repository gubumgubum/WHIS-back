package com.example.idea.comment.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Report {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 신고 대상이 되는 댓글
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    private Long reporterId; // 신고자 ID
    private String reason;   // 신고 사유
    private LocalDateTime createdAt = LocalDateTime.now();
}