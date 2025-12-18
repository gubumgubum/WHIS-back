package org.example.whiswriting.service;

import org.example.whiswriting.dto.PostCreateRequestDto;
import org.example.whiswriting.dto.PostResponseDto;
import org.example.whiswriting.entity.Post;

public PostResponseDto PostCreateService(PostCreateRequestDto request) {
    Post post = new Post();
    post.setTitle(request.getTitle());
    post.setContent(request.getContent());
    post.setAuthor(request.getAuthor());
    post.setAnonymous(request.isAnonymous());
    post.setCategory(request.getCategory());
    post.setImages(convertListToJson(request.getImages()));
    post.setLinks(convertListToJson(request.getLinks()));
    Post saved = postRepository.save(post);
    return toResponseDto(saved);
}