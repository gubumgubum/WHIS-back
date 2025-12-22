// 경로: src/main/java/com/example/idea/repository/CommentRepository.java
package com.example.idea.repository;

import com.example.idea.repository.Repository; // 엔티티 위치 확인!
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import com.example.idea.comment.model.Comment;

public interface Repository extends JpaRepository<Comment, Long> {

    // 댓글을 가져올 때 게시글(post) 정보를 한 번에 가져오도록 fetch join을 사용합니다.
    @Query("SELECT c FROM Comment c JOIN FETCH c.post WHERE c.userId = :userId ORDER BY c.createdAt DESC")
    List<Comment> findAllByUserIdWithPost(@Param("userId") Long userId);
}