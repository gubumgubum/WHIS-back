package org.example.whisauth.upload;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileService {

    private final String uploadDir = "uploads/profile/";

    public String saveProfileImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return "/images/default-profile.png";
        }

        try {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path savePath = Paths.get(uploadDir + fileName);

            Files.createDirectories(savePath.getParent());
            Files.write(savePath, file.getBytes());

            return "/uploads/profile/" + fileName;

        } catch (IOException e) {
            throw new RuntimeException("파일 저장 실패");
        }
    }
}