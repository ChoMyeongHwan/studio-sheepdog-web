package com.studiosheepdog.dognapper.board.application.service;

import com.studiosheepdog.dognapper.board.domain.aggregate.entity.Board;
import com.studiosheepdog.dognapper.board.domain.aggregate.entity.Comment;
import com.studiosheepdog.dognapper.board.domain.repository.BoardRepository;
import com.studiosheepdog.dognapper.board.domain.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    // 댓글 생성
    @Transactional
    public Comment createComment(Long boardId, Comment comment) {
        Optional<Board> board = boardRepository.findById(boardId);
        if (board.isPresent()) {
            Comment newComment = new Comment(comment.getBody(), comment.getWriter(), board.get());
            return commentRepository.save(newComment);
        } else {
            throw new IllegalArgumentException("게시글을 찾을 수 없습니다: " + boardId);
        }
    }

    // 댓글 조회
    public List<Comment> getComments(Long boardId) {
        Optional<Board> board = boardRepository.findById(boardId);
        return board.map(Board::getComments)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다: " + boardId));
    }

    // 댓글 수정
    @Transactional
    public Comment updateComment(Long id, Comment updatedComment) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다: " + id));
        comment.update(updatedComment.getBody());
        return comment;
    }

    // 댓글 삭제
    @Transactional
    public void deleteComment(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다: " + id));
        commentRepository.delete(comment);
    }
}
