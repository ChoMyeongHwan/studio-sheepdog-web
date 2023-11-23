package com.studiosheepdog.dognapper.board.application.controller;

import com.studiosheepdog.dognapper.board.application.dto.BoardDTO;
import com.studiosheepdog.dognapper.board.application.dto.WriteBoardDTO;
import com.studiosheepdog.dognapper.board.application.service.BoardService;
import com.studiosheepdog.dognapper.board.application.service.CommentService;
import com.studiosheepdog.dognapper.board.application.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final CommentService commentService;
    private final LikeService likeService;

    @GetMapping // 전체 조회
    public String listBoards(Model model, @PageableDefault(size = 9, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        model.addAttribute("boards", boardService.getAllBoards(pageable));
        return "board/list";
    }

    @GetMapping("/{id}") // 게시글 상세 조회
    public String viewBoard(@PathVariable Long id, Model model) {
        BoardDTO boardDTO = boardService.getBoard(id);
        model.addAttribute("board", boardDTO);
        model.addAttribute("comments", commentService.getComments(id));
        model.addAttribute("likes", likeService.getLikes(id));
        return "board/detail";
    }

    @GetMapping("/write") // 게시글 작성 페이지 이동
    public String writeBoardForm() {
        return "board/write";
    }

    @PostMapping("/write") // 게시글 작성
    public String writeBoard(@ModelAttribute WriteBoardDTO writeBoardDTO) {
        boardService.writeBoard(writeBoardDTO);
        return "redirect:/boards";
    }

    @GetMapping("/{id}/edit") // 게시글 수정 페이지 이동
    public String editBoardForm(@PathVariable Long id, Model model) {
        BoardDTO boardDTO = boardService.getBoard(id);
        model.addAttribute("board", boardDTO);
        return "board/edit";
    }

    @PostMapping("/{id}/edit") // 게시글 수정
    public String editBoard(@PathVariable Long id, @ModelAttribute WriteBoardDTO writeBoardDTO) {
        boardService.updateBoard(id, writeBoardDTO);
        return "redirect:/boards/" + id;
    }

    @PostMapping("/{id}/delete") // 게시글 삭제
    public String deleteBoard(@PathVariable Long id) {
        boardService.deleteBoard(id);
        return "redirect:/boards";
    }

}
