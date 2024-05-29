package com.codingrecipe.board.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtServiceImpl implements JwtService {
    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final long expirationMs = 300000; // 토큰 만료 시간 (5분)

    @Override
    public String getToken(String key, Object value, String name) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expirationMs);

        Map<String, Object> claims = new HashMap<>();
        claims.put(key, value);
        claims.put("name", name);

        JwtBuilder builder = Jwts.builder()
                .setClaims(claims)
                .setExpiration(expiration)
                .signWith(secretKey);

        return builder.compact();
    }

    @Override
    public String getEmailFromToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);

            // 이메일이 없는 경우 null을 반환하도록 수정
            return claims.getBody().get("email", String.class);
        } catch (ExpiredJwtException e) {
            throw new IllegalArgumentException("만료된 JWT 토큰입니다");
        } catch (Exception e) {
            // 이메일이 없는 경우 null을 반환하도록 수정
            return null;
        }
    }

}
