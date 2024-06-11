package com.codingrecipe.board.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

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
