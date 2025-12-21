package org.example.whisauth.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    private final long ACCESS_TOKEN_EXPIRE = 1000L * 60 * 30; // 30분

    public String createAccessToken(Long userId) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + ACCESS_TOKEN_EXPIRE);

        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setIssuedAt(now)
                .setExpiration(expiry)

                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
                .compact();
    }


    public Long getUserId(String token) {
        return Long.parseLong(
                Jwts.parser()
                        .setSigningKey(secretKey.getBytes()) // 여기도 .getBytes() 추가
                        .parseClaimsJws(token)
                        .getBody()
                        .getSubject()
        );
    }
}
