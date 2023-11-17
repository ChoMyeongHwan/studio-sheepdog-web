package com.studiosheepdog.dognapper.board.application.controller;

import com.studiosheepdog.dognapper.board.application.dto.WriteBoardDTO;
import com.studiosheepdog.dognapper.board.application.service.BoardService;
import com.studiosheepdog.dognapper.board.domain.aggregate.entity.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping // 전체 조회
    public String listBoards(Model model, @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        model.addAttribute("boards", boardService.getAllBoards(pageable));
        return "board/list";
    }

    @GetMapping("/{id}/detail") // 게시글 상세 조회
    public String viewBoard(@PathVariable Long id, Model model) {
        Board board = boardService.getBoard(id);
        model.addAttribute("board", board);
        return "board/detail";
    }

    @GetMapping("/write") // 게시글 작성 페이지 이동
    public String writeBoardForm() {
        return "board/write";
    }

    @PostMapping("/write") // 게시글 작성
    public String writeBoard(@ModelAttribute WriteBoardDTO writeBoardDTO) {
        boardService.writeBoard(writeBoardDTO);
        return "redirect:/board";
    }

    @GetMapping("/{id}/edit") // 게시글 수정 페이지 이동
    public String editBoardForm(@PathVariable Long id, Model model) {
        Board board = boardService.getBoard(id);
        model.addAttribute("board", board);
        return "board/edit";
    }

    @PostMapping("/{id}/edit") // 게시글 수정
    public String editBoard(@PathVariable Long id, WriteBoardDTO writeBoardDTO) {
        boardService.updateBoard(id, writeBoardDTO);
        return "redirect:/board/" + id + "/detail";
    }

    @PostMapping("/{id}/delete") // 게시글 삭제
    public String deleteBoard(@PathVariable Long id) {
        boardService.deleteBoard(id);
        return "redirect:/board";
    }
}
