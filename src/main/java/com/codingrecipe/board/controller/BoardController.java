package com.codingrecipe.board.controller;

import com.codingrecipe.board.entity.Board;
import com.codingrecipe.board.service.BoardService;
import com.codingrecipe.board.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController //RESTful 엔드포인트를 처리하는 컨트롤러임
@RequestMapping("/api/board") //모든 핸들러 메서드가 /api/board 경로에 매핑됨
public class BoardController {

    private final BoardService boardService;
    private final JwtService jwtService;

    @Autowired
    public BoardController(BoardService boardService, JwtService jwtService) {
        this.boardService = boardService;
        this.jwtService = jwtService;
    }

    @PostMapping("/write")
    public void boardWritePro(@RequestBody Board board, @RequestHeader("Authorization") String authorizationHeader) {
        // 헤더에서 토큰 추출
        String token = authorizationHeader.substring("Bearer ".length()); // Bearer 키워드 제거

        // 토큰을 이용하여 사용자 이메일 가져오기
        String email = jwtService.getEmailFromToken(token);

        // 새로운 게시글이므로 작성자 확인 없이 게시글 작성
        if (email != null) {
            boardService.write(board);
        } else {
            // 권한 없음 에러 처리
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }
    }


    @GetMapping("/list")
    public List<Board> boardList(
            @RequestParam(value = "page", defaultValue = "0") int page, // 페이지 번호
            @RequestParam(value = "size", defaultValue = "5") int size, // 페이지 크기
            @RequestParam(value = "sort", defaultValue = "id") String sort, // 정렬 기준
            @RequestParam(value = "direction", defaultValue = "DESC") String direction, // 정렬 방향
            @RequestParam(value = "searchKeyword", required = false) String searchKeyword) { // 검색 키워드 (선택적)

        if (searchKeyword == null) { // 검색 키워드가 없는 경우
            int offset = page * size; // 오프셋 계산
            return boardService.boardList(offset, size); // 서비스 클래스의 boardList 메서드 호출
        } else { // 검색 키워드가 있는 경우
            int offset = page * size; // 오프셋 계산
            return boardService.boardSearchList(searchKeyword, offset, size); // 서비스 클래스의 boardSearchList 메서드 호출
        }
    }

    @GetMapping("/view/{id}")
    public Board boardView(@PathVariable Integer id) {
        return boardService.boardView(id);
    }

    @DeleteMapping("/delete/{id}")
    public void boardDelete(@PathVariable Integer id) {
        boardService.boardDelete(id);
    }

    @PutMapping("/modify/{id}")
    public void boardModify(@PathVariable Integer id, @RequestBody Board board) {
        board.setId(id); // 게시물의 ID 설정
        boardService.boardModify(board); // 수정된 게시물을 서비스로 전달
    }
}