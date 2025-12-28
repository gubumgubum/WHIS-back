package com.example.idea.comment.controller; // 👈 현재 폴더 위치 확인!

import com.example.idea.ai.ReportValidationService;
import com.example.idea.comment.model.Comment;
import com.example.idea.comment.entity.Post;
import com.example.idea.comment.model.Report;
import com.example.idea.comment.repository.CommentRepository;
import com.example.idea.comment.repository.PostRepository;
import com.example.idea.comment.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/report")
public class ReportController {

    @Autowired private ReportRepository reportRepository;
    @Autowired private CommentRepository commentRepository;
    @Autowired private PostRepository postRepository;
    @Autowired private ReportValidationService reportValidationService;

    @PostMapping("/submit")
    public String report(@RequestParam String targetType,
                         @RequestParam Long targetId,
                         @RequestParam Long reporterId,
                         @RequestParam String reason,
                         @RequestParam String title,
                         @RequestParam String content) {

        String originalContent = "";

        // [1단계] 신고 대상에 따라 데이터 가져오기
        if ("COMMENT".equals(targetType)) {
            Comment comment = commentRepository.findById(targetId)
                    .orElseThrow(() -> new IllegalArgumentException("댓글이 없습니다."));
            originalContent = comment.getContent();
        } else if ("POST".equals(targetType)) {
            Post post = postRepository.findById(targetId)
                    .orElseThrow(() -> new IllegalArgumentException("게시글이 없습니다."));
            originalContent = "제목: " + post.getTitle() + "\n본문: " + post.getContent();
        }

        try {
            // [2단계] AI 검증
            String aiDecision = reportValidationService.validateReport(originalContent, reason, content);

            if (aiDecision.contains("[판단: 부적절]")) {
                return aiDecision;
            }

            // [3단계] DB 저장
            Report report = new Report();
            report.setTargetType(targetType);
            report.setTargetId(targetId);
            report.setReporterId(reporterId);
            report.setReason(reason);
            report.setTitle(title);
            report.setContent(content);
            reportRepository.save(report);

            return aiDecision; // "[판단: 적절]" 반환

        } catch (Exception e) {
            return "처리 중 오류 발생: " + e.getMessage();
        }
    }
}