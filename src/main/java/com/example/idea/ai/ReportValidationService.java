package ai; // 만약 폴더 위치가 다르면 패키지 이름을 수정하세요.

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Service;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class ReportValidationService {
    private final String apiKey;

    public ReportValidationService() {
        // .env 파일에서 키 로드
        this.apiKey = Dotenv.load().get("OPENAI_API_KEY");
    }

    public String validateReport(String content) throws Exception {
        // AI에게 줄 상세 지침 (System Prompt)
        String systemInstruction = "너는 온라인 커뮤니티의 신고 검토 전문가야. " +
                "입력받은 신고 내용이 정당한지 분석해줘. " +
                "응답은 반드시 다음 형식을 지켜줘: " +
                "[판단: 적절/부적절] 사유: (한 줄 요약)";

        // JSON 데이터 구성
        String jsonPayload = """
            {
                "model": "gpt-5.2",
                "messages": [
                    {"role": "system", "content": "%s"},
                    {"role": "user", "content": "신고할 내용: %s"}
                ]
            }
            """.formatted(systemInstruction, content);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}