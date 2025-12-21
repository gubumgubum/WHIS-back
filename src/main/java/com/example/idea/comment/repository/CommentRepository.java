package com.example.idea.comment.repository;

import com.example.idea.comment.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    long countByPostId(Long postId);

    // userId로 댓글 목록을 찾는 메서드 추가
    List<Comment> findByUserId(Long userId);
}