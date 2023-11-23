package com.studiosheepdog.dognapper.board.application.controller;

import com.studiosheepdog.dognapper.board.application.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/boards/{boardId}")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/like") // 좋아요
    @ResponseBody
    public ResponseEntity<?> addLike(@PathVariable Long boardId, @RequestParam Long userId) {
        likeService.addLike(boardId, userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/unlike") // 좋아요 취소
    @ResponseBody
    public ResponseEntity<?> removeLike(@PathVariable Long boardId, @RequestParam Long userId) {
        likeService.removeLike(boardId, userId);
        return ResponseEntity.ok().build();
    }
}