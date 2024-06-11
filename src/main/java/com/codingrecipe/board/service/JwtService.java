package com.codingrecipe.board.service;

public interface JwtService {
    String getToken(String email);
    String getEmailFromToken(String token);
}
