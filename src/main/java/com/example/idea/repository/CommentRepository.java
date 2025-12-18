package com.example.idea.repository;

import com.example.idea.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository를 상속받으면 save(), findAll() 등을 자동으로 쓸 수 있습니다.
public interface CommentRepository extends JpaRepository<Comment, Long> {
}