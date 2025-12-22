package com.example.idea.comment.controller;

import com.example.idea.Notification.notification.notification;
import com.example.idea.Notification.notification.NotificationRepository;
import com.example.idea.comment.model.Comment;
import com.example.idea.comment.model.Report;
import com.example.idea.comment.repository.CommentRepository;
import com.example.idea.comment.repository.PostRepository;
import com.example.idea.comment.repository.ReportRepository;
import com.example.idea.entity.Post;
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
    @GetMapping("/comment/check-my")
    @ResponseBody // JSON 데이터를 반환하기 위함
    public List<Comment> getMyComments(@RequestParam("userId") Long userId) {
        return commentRepository.findByUserId(userId);
    }

    @GetMapping("/comment/check-num")
    @ResponseBody
    public long getCommentCount(@RequestParam Long postId) {
        // DB에서 해당 게시글의 댓글 개수를 가져와 반환합니다.
        return commentRepository.countByPostId(postId);
    }

    // 2. 댓글 목록 페이지 (post_detail.html 연결)
    @GetMapping("/comment")
    public String list(Model model) {
        // 1. 모든 댓글 목록 가져오기
        List<Comment> commentList = this.commentRepository.findAll()
                .stream()
                .filter(c -> c.getParent() == null)
                .collect(Collectors.toList());
        model.addAttribute("commentList", commentList);

        // 2. 중요: HTML의 ${post.id}를 위해 게시글 정보를 모델에 담기
        // 테스트를 위해 우선 ID가 1인 게시글을 가져오도록 설정합니다.
        Post post = this.postRepository.findById(1L).orElse(null);

        // 만약 DB에 게시글이 하나도 없다면 에러 방지를 위해 가짜 객체라도 넣어줍니다.
        if (post == null) {
            post = new Post();
            post.setId(1L);
        }

        model.addAttribute("post", post); // 👈 이제 HTML에서 ${post.id}를 쓸 수 있습니다!

        return "post_detail";
    }

    @Autowired
    PostRepository postRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @PostMapping("/comment/create")
    public String create(@RequestParam String content,
                         @RequestParam Long userId,
                         @RequestParam Long postId) {

        Comment c = new Comment();
        c.setContent(content);
        c.setUserId(userId);
        c.setCreatedAt(LocalDateTime.now());

        Post post = this.postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
        c.setPost(post);
        this.commentRepository.save(c);

        // 2. 알림 생성 (내가 만든 Notification 클래스 사용)
        notification note = new notification();
        // Post 엔티티에 getUserId()와 getTitle()이 있는지 확인하세요.
        note.setReceiverId(post.getUserId());
        note.setMessage("회원님의 게시글 '" + post.getTitle() + "'에 새 댓글이 달렸습니다.");
        note.setRead(false);

        this.notificationRepository.save(note);

        return "redirect:/comment";
    }

    @Autowired
    private ReportRepository reportRepository; // 추가

    // --- 추가: 댓글 신고 API ---
    @PostMapping("/comment/report")
    public String report(@RequestParam Long commentId,
                         @RequestParam Long reporterId,
                         @RequestParam String reason) {

        // 1. 신고 대상 댓글이 존재하는지 확인
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다."));

        // 2. 신고 객체 생성 및 저장
        Report report = new Report();
        report.setComment(comment);
        report.setReporterId(reporterId);
        report.setReason(reason);
        reportRepository.save(report);

        return "redirect:/comment"; // 신고 후 목록으로 이동
    }

    // 1. 알림 조회 API
    @GetMapping("/comment/notification")
    @ResponseBody // JSON 형태로 알림 목록을 반환합니다.
    public List<notification> getNotifications(@RequestParam("userId") Long userId) {
        // DB에서 해당 사용자의 읽지 않은 알림만 가져옵니다.
        return notificationRepository.findByReceiverIdAndIsReadFalse(userId);
    }

    // 알림 읽음 처리 API
    @PostMapping("/comment/notification/read")
    @ResponseBody
    public String markAsRead(@RequestParam("notificationId") Long notificationId) {
        notification note = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new IllegalArgumentException("알림이 존재하지 않습니다."));
        note.setRead(true); // 읽음 상태로 변경
        notificationRepository.save(note);
        return "success";
    }

}