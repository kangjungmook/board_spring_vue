package com.codingrecipe.board.service;

import com.codingrecipe.board.entity.Board;
import com.codingrecipe.board.mapper.BoardMapper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    private final BoardMapper boardMapper;

    @Autowired
    public BoardService(BoardMapper boardMapper) {
        this.boardMapper = boardMapper;
    }

    // 게시물 등록
    public void write(Board board){
        boardMapper.write(board);
    }

    // 게시물 삭제
    public void boardDelete(int id){
        boardMapper.delete(id);
    }

    // 게시물 수정
    public void boardModify(Board board){
        boardMapper.modify(board);
    }

    // 모든 게시물을 페이지네이션하여 조회
    public List<Board> boardList(int offset, int limit) {
        RowBounds rowBounds = new RowBounds(offset, limit);
        return boardMapper.selectAll();
    }


    // 키워드로 게시물을 검색하여 페이지네이션하여 조회
    public List<Board> boardSearchList(String keyword, int offset, int limit) {
        RowBounds rowBounds = new RowBounds(offset, limit);
        return boardMapper.searchWith(keyword, rowBounds);
    }

    // 게시물 상세 조회
    public Board boardView(Integer id) {
        return boardMapper.selectById(id);
    }

    }
