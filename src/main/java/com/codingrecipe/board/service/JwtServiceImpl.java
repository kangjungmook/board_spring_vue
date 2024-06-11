package com.codingrecipe.board.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {
    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final long expirationMs = 3600000; // 토큰 만료 시간 (1시간)

    @Override
    public String getToken(String email, String name) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expirationMs);

        JwtBuilder builder = Jwts.builder()
                .setSubject(email)
                .claim("name", name)
                .setIssuedAt(now)
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

            return claims.getBody().getSubject();
        } catch (ExpiredJwtException e) {
            throw new IllegalArgumentException("만료된 JWT 토큰입니다", e);
        } catch (JwtException e) {
            throw new IllegalArgumentException("유효하지 않은 JWT 토큰입니다", e);
        } catch (Exception e) {
            throw new IllegalArgumentException("JWT 토큰 처리 중 오류가 발생했습니다", e);
        }
    }
}
