// PostService.java 또는 CommentService.java
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {

    @Transactional(readOnly = true) // 이 어노테이션을 붙여주세요!
    public Post getPostDetail(Long id) {
        return postRepository.findById(id).orElseThrow(...);
    }
}