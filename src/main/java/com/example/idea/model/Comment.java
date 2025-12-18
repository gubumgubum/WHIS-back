package com.example.idea.model; // 본인의 패키지명에 맞게 수정

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter  // Getter와 Setter를 자동으로 만들어줍니다.
public class Comment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT") // 긴 문장도 저장 가능하게 설정
    private String content;

    private LocalDateTime createDate;

    @ManyToOne // 여러 개의 답글이 하나의 부모 댓글에 달릴 수 있습니다.
    private Comment parent; // 부모 댓글

    @OneToMany(mappedBy = "parent", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<Comment> replies;
}