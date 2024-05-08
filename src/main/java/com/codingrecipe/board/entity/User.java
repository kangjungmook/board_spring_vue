package com.codingrecipe.board.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class User {

    @Id
    private String userId;
    private String userPw;
    private String userName;
}
