package com.example.idea.comment.repository; // 위치는 프로젝트 구조에 맞춰주세요

import com.example.idea.comment.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}