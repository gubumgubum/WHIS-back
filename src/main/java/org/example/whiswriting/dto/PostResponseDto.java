package org.example.whiswriting.dto;

import jdk.jfr.Category;

import java.time.LocalDateTime;
import java.util.List;

public class PostResponseDto {
    private Long id;
    private String title;
    private String content;
    private String author; // 익명이면 "익명"으로 처리
    private Category category;
    private List<String> images;
    private List<String> links;
    private LocalDateTime createdAt;
}
