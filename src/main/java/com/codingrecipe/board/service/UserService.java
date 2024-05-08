package com.codingrecipe.board.service;

import com.codingrecipe.board.entity.User;
import com.codingrecipe.board.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserMapper userMapper;

    @Autowired
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public void registerUser(User user) {
        System.out.println("디비에 저장된 사용자 정보 " + user);
        userMapper.registerUser(user);
    }

}