package com.codingrecipe.board.controller;

import com.codingrecipe.board.entity.User;
import com.codingrecipe.board.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/board")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        boolean isLoggedIn = authService.login(user.getUserId(), user.getUserPw());
        if (isLoggedIn) {
            return ResponseEntity.ok("로그인 성공");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패");
        }

    }
}

