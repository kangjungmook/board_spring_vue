package com.codingrecipe.board.service;

public interface JwtService {
    public String getToken(String key, Object value, String name);

    public String getEmailFromToken(String token);
}

