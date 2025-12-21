package com.example.idea.comment.controller;

import com.example.idea.comment.model.Comment;
import com.example.idea.comment.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    // 1. (API) 내가 쓴 댓글 조회 기능 추가
    @GetMapping("/api/comments/my/{userId}")
    @ResponseBody // JSON 데이터를 반환하기 위함
    public List<Comment> getMyComments(@PathVariable("userId") Long userId) {
        return commentRepository.findByUserId(userId);
    }

    // 2. 댓글 목록 페이지 (post_detail.html 연결)
    @GetMapping("/comment")
    public String list(Model model) {
        List<Comment> commentList = this.commentRepository.findAll()
                .stream()
                .filter(c -> c.getParent() == null) // getParent() 에러 해결
                .collect(Collectors.toList());
        model.addAttribute("commentList", commentList);
        return "post_detail";
    }

    // 3. 댓글 생성 (setCreatedAt으로 이름 수정)
    @PostMapping("/comment/create")
    public String create(@RequestParam String content, @RequestParam Long userId) {
        Comment c = new Comment();
        c.setContent(content);
        c.setUserId(userId); // 작성자 저장
        c.setCreatedAt(LocalDateTime.now()); //
        this.commentRepository.save(c);
        return "redirect:/comment";
    }
}