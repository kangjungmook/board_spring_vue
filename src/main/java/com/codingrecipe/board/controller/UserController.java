package com.codingrecipe.board.controller;

import com.codingrecipe.board.Dto.LoginDto;
import com.codingrecipe.board.Dto.ResponseDto;
import com.codingrecipe.board.Dto.SignUpDto;
import com.codingrecipe.board.service.UserService;
import com.codingrecipe.board.service.JwtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/board")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/signup")
    public ResponseDto signUp(@RequestBody SignUpDto signUpDto) {
        boolean success = userService.signUp(signUpDto);
        if (success) {
            return ResponseDto.setSuccess("회원가입 성공");
        } else {
            return ResponseDto.setFailed("비밀번호를 다시 확인해주세요");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        boolean success = userService.login(loginDto);
        if (success) {
            String token = jwtService.getToken("Email", loginDto.getEmail(), loginDto.getName());
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return ResponseEntity.ok(response);
        } else {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

}
