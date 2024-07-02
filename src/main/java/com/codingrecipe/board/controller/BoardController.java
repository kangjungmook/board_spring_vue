package com.codingrecipe.board.controller;

import com.codingrecipe.board.Dto.CommentDto;
import com.codingrecipe.board.entity.Board;
import com.codingrecipe.board.entity.Comment;
import com.codingrecipe.board.service.BoardService;
import com.codingrecipe.board.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/board")
public class BoardController {

    @Autowired
    private CommentService commentService;

    private final BoardService boardService;
    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }


    @PostMapping("/write")
    public ResponseEntity<?> boardWritePro(@RequestBody Board board) {
        try {
            boardService.write(board);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @GetMapping("/list")
    public List<Board> boardList(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam(value = "sort", defaultValue = "id") String sort,
            @RequestParam(value = "direction", defaultValue = "DESC") String direction,
            @RequestParam(value = "searchKeyword", required = false) String searchKeyword) {

        int offset = page * size;
        if (searchKeyword == null) {
            return boardService.boardList(offset, size);
        } else {
            return boardService.boardSearchList(searchKeyword, offset, size);
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
    public ResponseEntity<?> boardModify(@PathVariable Integer id, @RequestBody Board board) {
        try {
            board.setId(id);
            boardService.boardModify(board);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PostMapping("/add")
    public ResponseEntity<?> addComment(@RequestBody CommentDto commentDto) {
            Comment comment = new Comment();
            comment.setBoardId(commentDto.getBoardId());
            comment.setEmail(commentDto.getEmail());
            comment.setContent(commentDto.getContent());
            comment.setCreatedAt(LocalDateTime.now());
            commentService.addComment(comment);
            return ResponseEntity.ok().build();
    }
}






















