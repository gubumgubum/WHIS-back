package com.example.idea.report.controller; // 본인의 실제 패키지 경로로 수정하세요!

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/report") // 노션에 적힌 대로 /report로 설정
public class ReportController {

    // '파일 추가' 기능 (POST 방식)
    @PostMapping("/upload")
    public ResponseEntity<String> uploadReportFile(@RequestParam("file") MultipartFile file) {

        // 1. 파일이 비어있는지 체크
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("파일이 선택되지 않았습니다.");
        }

        try {
            // 2. 저장할 경로 설정 (프로젝트 루트 폴더의 uploads 폴더)
            String uploadDir = System.getProperty("user.dir") + "/uploads/";
            File folder = new File(uploadDir);

            // 폴더가 없으면 새로 만들기
            if (!folder.exists()) {
                folder.mkdirs();
            }

            // 3. 파일명 중복 방지 (UUID 사용)
            String originalFileName = file.getOriginalFilename();
            String savedFileName = UUID.randomUUID().toString() + "_" + originalFileName;

            // 4. 서버 PC에 실제 파일 저장
            File destination = new File(uploadDir + savedFileName);
            file.transferTo(destination);

            return ResponseEntity.ok("파일 업로드 성공! 경로: " + uploadDir + savedFileName);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("서버 오류로 파일 저장에 실패했습니다.");
        }
    }
}