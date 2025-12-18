package org.example.whiswriting.controller;

package org.example.whiswriting.controller;

import org.example.whiswriting.dto.PostCreateRequestDto;
import org.example.whiswriting.dto.PostResponseDto;
import org.example.whiswriting.entity.Post;
import org.example.whiswriting.repository.PostRepository;
import org.example.whiswriting.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostAPI {

    private final PostService postService;

    @Autowired
    public PostAPI(PostService postService) {
        this.postService = postService;
    }

    private final PostRepository postRepository;

    public PostResponseDto createPost(PostCreateRequestDto request) {
        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .category(request.getCategory())
                .anonymous(request.isAnonymous())
                .imageUrl(request.getImageUrl())
                .link(request.getLink())
                .build();

        postRepository.save(post);

        return new PostResponseDto(post);
    }

    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(@RequestBody PostCreateRequestDto request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(postService.createPost(request));
    }

}
