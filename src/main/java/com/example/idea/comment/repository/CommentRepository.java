package com.example.idea.comment.repository;

import com.example.idea.comment.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    long countByPostId(Long postId);

    // userId로 댓글 목록을 찾는 메서드 추가
    List<Comment> findByUserId(Long userId);

    @Query("select c from Comment c " +
            "join fetch c.post " + // Post 정보를 한 번에 같이 긁어옴
            "where c.parent is null") // 최상위 댓글만 조회
    List<Comment> findAllRootCommentsWithPost();
}