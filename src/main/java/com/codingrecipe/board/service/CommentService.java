package com.codingrecipe.board.service;

import com.codingrecipe.board.entity.Comment;
import com.codingrecipe.board.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;

    public void addComment(Comment comment){
        commentMapper.insertComment(comment);
    }

}