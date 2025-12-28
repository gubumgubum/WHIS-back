package org.example.postlike.service;

import lombok.RequiredArgsConstructor;
import org.example.postlike.domain.LikeEntity;
import org.example.postlike.domain.PostEntity;
import org.example.postlike.dto.LikeDto;
import org.example.postlike.dto.PostResponseDto;
import org.example.postlike.repository.LikeRepository;
import org.example.postlike.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;

    // ★ 이 줄 딱 하나만 추가했습니다. (게시글 정보를 가져와야 하니까요)
    private final PostRepository postRepository;

    // [기존 코드 100% 유지]
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

    // [기존 코드 100% 유지]
    @Transactional(readOnly = true)
    public long getLikeCount(Long postId) {
        return likeRepository.countByPostId(postId);
    }

    // ★ [여기만 수정함] List<Long> -> List<PostResponseDto> 로 변경
    @Transactional(readOnly = true)
    public List<PostResponseDto> getLikedPosts(Long userId) {

        // 1. 좋아요 목록 가져오기 (기존 로직 활용)
        List<LikeEntity> likes = likeRepository.findAllByUserId(userId);

        // 2. 게시글 ID만 추출
        List<Long> postIds = likes.stream()
                .map(LikeEntity::getPostId)
                .collect(Collectors.toList());

        // 3. 실제 게시글 정보 조회 (PostRepository 사용)
        List<PostEntity> posts = postRepository.findAllById(postIds);

        // 4. DTO로 변환하여 반환
        return posts.stream()
                .map(post -> new PostResponseDto(
                        post.getId(),
                        post.getTitle(),
                        post.getContent(),
                        post.getCategory().toString(), // 카테고리
                        post.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }

    // [기존 코드 100% 유지]
    public void sendLikeAlarm(LikeDto.AlarmRequest request) {
        System.out.println("알림 전송 [Target: " + request.getTargetUserId() + "]: " + request.getMessage());
    }
}