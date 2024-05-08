package com.codingrecipe.board.service;

import com.codingrecipe.board.entity.User;
import com.codingrecipe.board.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserMapper userMapper;

    public boolean login(String userId, String password) {
        User user = userMapper.findByUserId(userId);
        if (user != null && user.getUserPw().equals(password)) {
            System.out.println("로그인 성공: " + userId);
            return true; // 로그인 성공
        } else {
            // 사용자가 등록되지 않은 경우
            if (user == null) {
                System.out.println("사용자가 등록되지 않았습니다: " + userId);
            } else {
                System.out.println("비밀번호가일치하지 않습니다: " + userId);
            }
            return false; // 로그인 실패
        }
    }
 }
