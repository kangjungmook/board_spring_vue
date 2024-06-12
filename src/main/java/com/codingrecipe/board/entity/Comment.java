package com.codingrecipe.board.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private int id;
    private int board_id;
    private String email;
    private String content;
    private String created_at;
}
