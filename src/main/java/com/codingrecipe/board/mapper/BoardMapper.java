package com.codingrecipe.board.mapper;

import com.codingrecipe.board.entity.Board;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

@Mapper
public interface BoardMapper {
    // 게시물 등록
    void write(Board board);

    // 게시물 삭제
    void delete(int id);

    // 게시물 수정
    void modify(Board board);

    // 키워드로 게시물을 검색하여 페이지네이션하여 조회
    List<Board> searchWith(@Param("keyword") String keyword, RowBounds rowBounds);

    // 게시물 상세 조회
    Board selectById(@Param("id") Integer id);

    // 모든 게시물을 조회하는 메서드 추가
    List<Board> selectAll();

    List<Board> selectAllWithUserInfo();

}
