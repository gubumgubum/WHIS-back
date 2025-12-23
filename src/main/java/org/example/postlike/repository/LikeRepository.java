package org.example.postlike.repository;

import org.example.postlike.domain.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<LikeEntity, Long> {
    Optional<LikeEntity> findByUserIdAndPostId(Long userId, Long postId);
    long countByPostId(Long postId);
    List<LikeEntity> findAllByUserId(Long userId);
}