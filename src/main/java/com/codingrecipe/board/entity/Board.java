package com.codingrecipe.board.entity;

import jakarta.persistence.Id;
import lombok.Data;

@Data
public class Board {

    @Id
    private Integer id;
    private String title;
    private String content;
    private String userId;
}
