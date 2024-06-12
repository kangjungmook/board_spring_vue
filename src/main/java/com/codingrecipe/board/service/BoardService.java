package com.codingrecipe.board.service;

import com.codingrecipe.board.entity.Board;
import com.codingrecipe.board.mapper.BoardMapper;
import com.codingrecipe.board.mapper.UserMapper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    private final BoardMapper boardMapper;
    private final UserMapper userMapper;

    @Autowired
    public BoardService(BoardMapper boardMapper, UserMapper userMapper) {
        this.boardMapper = boardMapper;
        this.userMapper = userMapper;
    }

    // 게시물 등록
    public void write(Board board) throws Exception {
        if (userMapper.existsByEmail(board.getEmail())) {
            boardMapper.write(board);
        } else {
            throw new Exception("유효한 이메일이 필요합니다.");
        }
    }

    // 게시물 삭제
    public void boardDelete(int id) {
        boardMapper.delete(id);
    }

    // 게시물 수정
    public void boardModify(Board board) throws Exception {
        Board existingBoard = boardMapper.selectById(board.getId());
        if (existingBoard == null) {
            throw new Exception("해당 게시물이 존재하지 않습니다.");
        }
        if (!existingBoard.getEmail().equals(board.getEmail())) {
            throw new Exception("수정할 권한이 없습니다.");
        }
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
