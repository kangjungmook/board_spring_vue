package com.codingrecipe.board.mapper;

import com.codingrecipe.board.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    void registerUser(User user);
    User findByUserId(String userId);
}
