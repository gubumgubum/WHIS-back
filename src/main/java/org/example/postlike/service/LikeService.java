package org.example.postlike.service;

import org.example.postlike.domain.LikeEntity;
import org.example.postlike.dto.LikeDto;
import org.example.postlike.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;

    // 좋아요 토글 (있으면 삭제, 없으면 생성)
    @Transactional
    public String toggleLike(LikeDto.Request request) {
        Optional<LikeEntity> existingLike = likeRepository.findByUserIdAndPostId(request.getUserId(), request.getPostId());

        if (existingLike.isPresent()) {
            likeRepository.delete(existingLike.get());
            return "좋아요 취소";
        } else {
            likeRepository.save(LikeEntity.builder()
                    .userId(request.getUserId())
                    .postId(request.getPostId())
                    .build());
            return "좋아요 성공";
        }
    }

    @Transactional(readOnly = true)
    public long getLikeCount(Long postId) {
        return likeRepository.countByPostId(postId);
    }

    @Transactional(readOnly = true)
    public List<Long> getLikedPosts(Long userId) {
        return likeRepository.findAllByUserId(userId).stream()
                .map(LikeEntity::getPostId)
                .collect(Collectors.toList());
    }

    public void sendLikeAlarm(LikeDto.AlarmRequest request) {
        System.out.println("알림 전송 [Target: " + request.getTargetUserId() + "]: " + request.getMessage());
    }
}