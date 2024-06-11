package com.codingrecipe.board.service;

public interface JwtService {
    String getToken(String email, String name);
    String getEmailFromToken(String token);
}
