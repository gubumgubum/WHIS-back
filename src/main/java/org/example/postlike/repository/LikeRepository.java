package org.example.postlike.repository;

import org.example.postlike.domain.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LikeRepository extends JpaRepository<LikeEntity, Long> {

    // ★ 서비스에서 findAllByUserId 라고 불렀으니, 여기서도 똑같이 맞춰줘야 합니다.
    List<LikeEntity> findAllByUserId(Long userId);

    // (기존 코드 유지)
    java.util.Optional<LikeEntity> findByUserIdAndPostId(Long userId, Long postId);
    long countByPostId(Long postId);
}