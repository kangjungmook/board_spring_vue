package com.codingrecipe.board.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Board {
    private int id;
    private String email;
    private String title;
    private String content;
    private String created_at;
    private String name;
}
