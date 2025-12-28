package org.example.postlike.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PostResponseDto {
    private Long id;
    private String title;
    private String content;
    private String category;
    private LocalDateTime createdAt;

    // 생성자 (데이터를 넣는 입구)
    public PostResponseDto(Long id, String title, String content, String category, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.category = category;
        this.createdAt = createdAt;
    }
}