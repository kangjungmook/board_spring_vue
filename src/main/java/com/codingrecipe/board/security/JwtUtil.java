package com.codingrecipe.board.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

s
@Component
public class JwtUtil {
    //private static final String SECRET_KEY = "";
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 10; // 10시간

    // 토큰 생성
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email) // 주체 설정 (여기서는 이메일)
                .setIssuedAt(new Date()) // 발급 시간 설정
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 만료 시간 설정
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // 서명 알고리즘과 비밀 키 설정
                .compact(); // 토큰 생성
    }

    // 토큰에서 이메일 추출
    public String extractEmail(String token) {
        return getClaims(token).getSubject();
    }

    // 토큰 유효성 검사
    public boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

    // 클레임 추출
    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY) // 서명 검증을 위한 비밀 키 설정
                .parseClaimsJws(token)
                .getBody();
    }

    // 토큰 만료 여부 확인
    private boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }
}
