package com.codingrecipe.board.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private int id;
    private int boardId;
    private String email;
    private String content;
    private LocalDateTime createdAt;
}
