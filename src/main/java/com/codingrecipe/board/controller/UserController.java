package com.codingrecipe.board.controller;

import com.codingrecipe.board.Dto.LoginDto;
import com.codingrecipe.board.Dto.ResponseDto;
import com.codingrecipe.board.Dto.SignUpDto;
import com.codingrecipe.board.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/board")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseDto<?> login(@RequestBody LoginDto loginDto) {
        boolean success = userService.login(loginDto);
        if (success) {
            return ResponseDto.setSuccess("로그인 성공");
        } else {
            return ResponseDto.setFailed("로그인 실패");
        }
    }

    @PostMapping("/signup")
    public ResponseDto<?> signUp(@RequestBody SignUpDto signUpDto) {
        boolean success = userService.signUp(signUpDto);
        if (success) {
            return ResponseDto.setSuccess("회원가입 성공");
        } else {
            return ResponseDto.setFailed("이미 존재하는 이메일 주소입니다. 다른 이메일을 사용해주세요.");
        }
    }
}
