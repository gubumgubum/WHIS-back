package com.example.idea.comment.model;

import com.example.idea.entity.Post;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Comment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private LocalDateTime createdAt = LocalDateTime.now(); // 이름을 createdAt으로 통일

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    // --- 추가: 내가 쓴 댓글 조회를 위한 필드 ---
    private Long userId;

    // --- 추가: 대댓글 기능을 위한 필드 (image_a393c0 에러 해결) ---
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Comment> children = new ArrayList<>();
}