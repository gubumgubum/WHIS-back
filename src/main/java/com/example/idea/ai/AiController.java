package ai;

import ai.ReportValidationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController // 이 클래스가 웹 요청을 받는 컨트롤러임을 선언
public class AiController {

    private final ReportValidationService validationService;

    // 생성자를 통해 아까 만든 서비스를 주입받습니다.
    public AiController(ReportValidationService validationService) {
        this.validationService = validationService;
    }

    @GetMapping("/api/validate") // 브라우저에서 접속할 주소 설정
    public String validate(@RequestParam(value = "content") String content) {
        try {
            // AI 서비스에 신고 내용을 전달하고 결과를 받음
            return validationService.validateReport(content);
        } catch (Exception e) {
            return "에러 발생: " + e.getMessage();
        }
    }
}