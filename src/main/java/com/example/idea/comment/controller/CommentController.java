package com.example.idea.comment.controller;

import com.example.idea.alarm.service.*;
import com.example.idea.alarm.repository.AlarmRepository;
import com.example.idea.alarm.model.Alarm;
import com.example.idea.comment.notification.notification;
import com.example.idea.comment.notification.NotificationRepository;
import com.example.idea.comment.model.Comment;
import com.example.idea.comment.model.Report;
import com.example.idea.comment.repository.CommentRepository;
import com.example.idea.comment.repository.PostRepository;
import com.example.idea.comment.repository.ReportRepository;
import com.example.idea.comment.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

// 1. @Controller 대신 @RestController를 사용하면 모든 메서드에 @ResponseBody가 붙은 효과가 납니다.
@RestController
@RequestMapping("/comment") // 경로에 /api를 붙여주는 것이 관례입니다.
@CrossOrigin(origins = "*")    // 프론트엔드 연결을 위한 CORS 허용
public class CommentController {

    @Autowired private CommentRepository commentRepository;
    @Autowired private PostRepository postRepository;
    @Autowired private NotificationRepository notificationRepository;
    @Autowired private ReportRepository reportRepository;

    // 1. 내가 쓴 댓글 조회 (JSON 반환)
    @GetMapping("/check-my")
    public List<Comment> getMyComments(@RequestParam("userId") Long userId) {
        return commentRepository.findByUserId(userId);
    }

    // 2. 댓글 개수 조회
    @GetMapping("/check-num")
    public long getCommentCount(@RequestParam Long postId) {
        return commentRepository.countByPostId(postId);
    }

    // 3. 댓글 목록 조회 (기존의 list 메서드 대체)
    @GetMapping("/list")
    public List<Comment> getCommentList() {
        // 부모 댓글이 없는(최상위 댓글) 것들만 가져와서 반환
        return this.commentRepository.findAll()
                .stream()
                .filter(c -> c.getParent() == null)
                .collect(Collectors.toList());
    }

    // 4. 댓글 및 답글 작성
    @PostMapping("/post")
    public Comment create(@RequestParam String content,
                          @RequestParam Long userId,
                          @RequestParam Long postId,
                          @RequestParam(required = false) Long parentId) {

        Comment c = new Comment();
        c.setContent(content);
        c.setUserId(userId);
        c.setCreatedAt(LocalDateTime.now());

        Post post = this.postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
        c.setPost(post);

        Comment parentComment = null;
        if (parentId != null) {
            parentComment = this.commentRepository.findById(parentId).orElse(null);
            c.setParent(parentComment);
        }

        Comment savedComment = this.commentRepository.save(c);

        // --- 알림 생성 로직 (동일) ---
        notification note = new notification();
        if (parentComment != null) {
            note.setReceiverId(parentComment.getUserId());
            note.setMessage("회원님의 댓글에 답글이 달렸습니다: " + content);
        } else {
            note.setReceiverId(post.getUserId());
            note.setMessage("회원님의 게시글 '" + post.getTitle() + "'에 새 댓글이 달렸습니다.");
        }
        note.setRead(false);
        this.notificationRepository.save(note);

        return savedComment; // 👈 리다이렉트 대신 생성된 객체를 반환합니다.
    }

    @Autowired
    private AlarmService alarmService; // 👈 서비스 주입

    @PostMapping("/report")
    public String report(@RequestParam Long commentId,
                         @RequestParam Long reporterId,
                         @RequestParam String reason) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다."));

        // 1. 신고 데이터 저장 (Controller의 본래 역할)
        Report report = new Report();
        report.setComment(comment);
        report.setReporterId(reporterId);
        report.setReason(reason);
        reportRepository.save(report);

        // 2, 3번 삭제 후 서비스 호출로 대체!
        alarmService.sendReportConfirmation(reporterId, reason); // 신고자 알림
        alarmService.sendReportWarning(comment.getUserId(), "댓글"); // 피신고자 알림

        return "신고가 접수되었습니다.";
    }

    // 6. 알림 조회
    @GetMapping("/notification")
    public List<notification> getNotifications(@RequestParam("userId") Long userId) {
        return notificationRepository.findByReceiverIdAndIsReadFalse(userId);
    }

    // 7. 알림 읽음 처리
    @PostMapping("/notification/read")
    public String markAsRead(@RequestParam("notificationId") Long notificationId) {
        notification note = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new IllegalArgumentException("알림이 존재하지 않습니다."));
        note.setRead(true);
        notificationRepository.save(note);
        return "success";
    }
}