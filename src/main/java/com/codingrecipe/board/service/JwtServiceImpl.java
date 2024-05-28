package com.codingrecipe.board.service;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

@Service
public class JwtServiceImpl implements JwtService {
    private Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    @Override
    public String getToken(String key, Object value) {
        Date expTime = new Date();
        expTime.setTime(expTime.getTime() + 1000 * 60 * 5); // 5분

        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("typ", "JWT");
        headerMap.put("alg", "HS256");

        Map<String, Object> payloadMap = new HashMap<>();
        payloadMap.put(key, value);

        JwtBuilder builder = Jwts.builder()
                .setHeader(headerMap)
                .setClaims(payloadMap)
                .setExpiration(expTime)
                .signWith(secretKey, SignatureAlgorithm.HS256);

        return builder.compact();
    }
}

