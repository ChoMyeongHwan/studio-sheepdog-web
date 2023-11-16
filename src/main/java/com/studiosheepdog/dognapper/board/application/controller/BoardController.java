package com.studiosheepdog.dognapper.board.application.controller;

import com.studiosheepdog.dognapper.board.application.dto.WriteBoardDTO;
import com.studiosheepdog.dognapper.board.application.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping
    public String listBoards() {

        return "board/list";
    }

    @GetMapping("/write") // 게시글 작성 페이지 이동
    public String writeBoardForm(Model model) {
        model.addAttribute("writeBoardDTO", new WriteBoardDTO());
        return "board/write";
    }

    @PostMapping("/write") // 게시글 작성
    public String writeBoard(@ModelAttribute WriteBoardDTO writeBoardDTO, Model model) {
        boardService.writeBoard(writeBoardDTO);
        return "redirect:/board";
    }
}
