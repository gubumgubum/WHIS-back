package org.example.postlike.repository;

import org.example.postlike.domain.PostEntity; // ★ 님의 게시글 파일 이름
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

// 이 파일이 있어야 빨간 줄이 사라집니다!
public interface PostRepository extends JpaRepository<PostEntity, Long> {

}