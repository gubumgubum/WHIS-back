package org.example.whiswriting.repository;

import org.example.whiswriting.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p WHERE " +
            "(:q IS NULL OR LOWER(p.title) LIKE LOWER(CONCAT('%', :q, '%')) " +
            "OR LOWER(p.content) LIKE LOWER(CONCAT('%', :q, '%')))")
    Page<Post> search(@Param("q") String q, Pageable pageable);
}