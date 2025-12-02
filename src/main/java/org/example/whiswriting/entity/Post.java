package org.example.whiswriting.entity;


import jakarta.persistence.*;
import jdk.jfr.Category;

import java.time.LocalDateTime;

public class Post {

    @Entity
    @Table(name = "post")
    public class Post {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String title;

        @Column(nullable = false, columnDefinition = "TEXT")
        private String content;

        @Column(nullable = false)
        private String author; // 익명이어도 DB에는 저장

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private Category category;

        @Column(nullable = false)
        private boolean anonymous;

        @Column(columnDefinition = "TEXT")
        private String images; // JSON 저장

        @Column(columnDefinition = "TEXT")
        private String links;  // JSON 저장

        @Column(nullable = false)
        private LocalDateTime createdAt = LocalDateTime.now();
    }

}