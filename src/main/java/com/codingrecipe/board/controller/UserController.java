package com.codingrecipe.board.controller;

import com.codingrecipe.board.Dto.LoginDto;
import com.codingrecipe.board.Dto.ResponseDto;
import com.codingrecipe.board.Dto.SignUpDto;
import com.codingrecipe.board.service.UserService;
import com.codingrecipe.board.service.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

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
            return ResponseDto.setFailed("이미 존재하는 이메일 주소입니다. 다른 이메일을 사용해주세요.");
        }
    }

    @PostMapping("/login") //여기 고쳐야함
    public void login(@RequestBody LoginDto loginDto, HttpServletResponse response) {
        boolean success = userService.login(loginDto);
        if (success) {
            String token = jwtService.getToken("Email", loginDto.getEmail());
            try {
                String encodedToken = URLEncoder.encode(token, StandardCharsets.UTF_8.toString());
                Cookie cookie = new Cookie("Authorization", "Bearer " + encodedToken);
                cookie.setHttpOnly(true);
                cookie.setSecure(true);
                cookie.setPath("/");
                response.addCookie(cookie);
                response.setStatus(HttpServletResponse.SC_OK);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace(); // Handle the exception appropriately
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }



}
