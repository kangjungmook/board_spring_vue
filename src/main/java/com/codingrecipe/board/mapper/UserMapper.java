package com.codingrecipe.board.mapper;


import com.codingrecipe.board.Dto.SignUpDto;
import org.apache.ibatis.annotations.Mapper;
import com.codingrecipe.board.Dto.LoginDto;

@Mapper
public interface UserMapper {
    int login(LoginDto loginDto);

    int signUp(SignUpDto signUpDto);

    boolean existsByEmail(String email);

}


