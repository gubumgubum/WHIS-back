package org.example.whisauth.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.whisauth.auth.JwtProvider;
import org.example.whisauth.user.User;
import org.example.whisauth.user.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        // 토큰이 있는 경우에만 인증 로직 실행
        if (header != null && header.startsWith("Bearer ")) {
            try {
                String token = header.substring(7);
                Long userId = jwtProvider.getUserId(token);

                // 유저가 없을 경우 예외를 던지지 않고 그냥 넘어가도록 수정
                userRepository.findById(userId).ifPresent(user -> {
                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(user, null, List.of());
                    SecurityContextHolder.getContext().setAuthentication(auth);
                });
            } catch (Exception e) {
                // 토큰 파싱 중 에러(만료 등)가 나도 일단 필터는 통과시켜야 permitAll이 작동함
                logger.error("Could not set user authentication in security context", e);
            }
        }

        filterChain.doFilter(request, response); // 반드시 실행되어야 함
    }
}

