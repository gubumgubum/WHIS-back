package org.example.whiswriting.service;


import org.example.whiswriting.dto.PostDto;
import org.example.whiswriting.entity.Post;
import org.example.whiswriting.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Page<PostDto> getAllPosts(int page, int size, String sortBy, String direction, String q) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Post> postPage = postRepository.search(
                q == null || q.isBlank() ? null : q,
                pageable
        );

        List<PostDto> dtos = postPage.getContent().stream()
                .map(this::toDto)
                .collect(Collectors.toList());

        return new PageImpl<>(dtos, pageable, postPage.getTotalElements());
    }

    private PostDto toDto(Post p) {
        PostDto d = new PostDto();
        d.setId(p.getId());
        d.setTitle(p.getTitle());
        d.setContent(p.getContent());
        d.setAuthor(p.getAuthor());
        d.setCreatedAt(p.getCreatedAt());
        return d;
    }
}
