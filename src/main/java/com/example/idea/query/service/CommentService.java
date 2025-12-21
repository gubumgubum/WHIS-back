// 경로: src/main/java/com/example/idea/query/service/CommentService.java
package com.example.idea.query.service;

import com.example.idea.query.dto.MyCommentResponse;
import com.example.idea.repository.CommentRepository;
// 중요: Comment 엔티티 경로
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;

    public List<MyCommentResponse> getMyComments(Long userId) {
        return commentRepository.findAllByUserIdWithPost(userId).stream()
                .map(c -> new MyCommentResponse(
                        c.getId(),
                        c.getContent(),
                        c.getCreatedAt(),
                        c.getPost().getId(),
                        c.getPost().getTitle() // Post 엔티티에 getTitle()이 있어야 함
                ))
                .collect(Collectors.toList());
    }
}