package com.codingrecipe.board.service;

import com.codingrecipe.board.Dto.LoginDto;
import com.codingrecipe.board.Dto.SignUpDto;
import com.codingrecipe.board.mapper.UserMapper;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public boolean login(LoginDto loginDto) {
        return userMapper.login(loginDto) > 0;
    }

    public boolean signUp(SignUpDto signUpDto) {
        // 비밀번호 확인
        if (!signUpDto.getPassword().equals(signUpDto.getConfirmPassword())) {
            return false;
        }
        return userMapper.signUp(signUpDto) > 0;
    }
    public String getNameByEmail(String email) {
        return userMapper.getNameByEmail(email);
    }

}
