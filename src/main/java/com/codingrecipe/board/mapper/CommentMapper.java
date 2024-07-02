package com.codingrecipe.board.mapper;

import com.codingrecipe.board.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
    void insertComment(Comment comment);
    List<Comment> selectCommentsByBoardId(int boardIDd);
    void deleteComment(int id);

}
