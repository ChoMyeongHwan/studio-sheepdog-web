package com.studiosheepdog.dognapper.board.application.controller;

import com.studiosheepdog.dognapper.board.application.dto.WriteCommentDTO;
import com.studiosheepdog.dognapper.board.application.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/boards/{boardId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping // 댓글 작성
    public String writeComment(@PathVariable Long boardId, @ModelAttribute WriteCommentDTO writeCommentDTO) {
        commentService.writeComment(boardId, writeCommentDTO);
        return "redirect:/boards/" + boardId;
    }

    @PostMapping("/{commentId}/edit") // 댓글 수정
    public String editComment(@PathVariable Long boardId, @PathVariable Long commentId, @ModelAttribute WriteCommentDTO writeCommentDTO) {
        commentService.updateComment(commentId, writeCommentDTO);
        return "redirect:/boards/" + boardId;
    }

    @PostMapping("/{commentId}/delete") // 댓글 삭제
    public String deleteComment(@PathVariable Long boardId, @PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return "redirect:/boards/" + boardId;
    }
}
