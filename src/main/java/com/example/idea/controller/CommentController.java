package com.example.idea.controller;

import com.example.idea.model.Comment;
import com.example.idea.repository.CommentRepository;
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

    // 1. 댓글 및 답글 목록을 보여주는 페이지
    @GetMapping("/comment")
    public String list(Model model) {
        // 모든 댓글을 가져와서 '부모가 없는(null) 최상위 댓글'만 필터링합니다.
        List<Comment> commentList = this.commentRepository.findAll()
                .stream()
                .filter(c -> c.getParent() == null)
                .collect(Collectors.toList());

        model.addAttribute("commentList", commentList);
        return "post_detail";
    }

    // 2. 새로운 댓글을 저장하는 기능
    @PostMapping("/comment/create")
    public String create(@RequestParam String content) {
        Comment c = new Comment();
        c.setContent(content);
        c.setCreateDate(LocalDateTime.now());
        this.commentRepository.save(c);
        return "redirect:/comment";
    }

    // 3. 댓글 또는 답글을 삭제하는 기능
    @GetMapping("/comment/delete/{id}")
    public String deleteComment(@PathVariable("id") Long id) {
        commentRepository.deleteById(id);
        return "redirect:/comment";
    }

    // 4. 특정 댓글에 답글을 저장하는 기능
    @PostMapping("/reply/create/{id}")
    public String createReply(@PathVariable("id") Long id, @RequestParam String content) {
        Comment parent = this.commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));

        Comment reply = new Comment();
        reply.setContent(content);
        reply.setCreateDate(LocalDateTime.now());
        reply.setParent(parent); // 부모 댓글과 연결

        this.commentRepository.save(reply);
        return "redirect:/comment";
    }
}