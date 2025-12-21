package com.example.idea.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title; // 서비스 코드의 getTitle()과 연결됨

    private String content;

    private LocalDateTime createdAt = LocalDateTime.now();
}