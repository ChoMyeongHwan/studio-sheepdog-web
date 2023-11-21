package com.studiosheepdog.dognapper.board.application.service;

import com.studiosheepdog.dognapper.board.application.dto.CommentDTO;
import com.studiosheepdog.dognapper.board.application.dto.WriteCommentDTO;
import com.studiosheepdog.dognapper.board.domain.aggregate.entity.Board;
import com.studiosheepdog.dognapper.board.domain.aggregate.entity.Comment;
import com.studiosheepdog.dognapper.board.domain.repository.BoardRepository;
import com.studiosheepdog.dognapper.board.domain.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    /* 댓글 조회 */
    @Transactional(readOnly = true)
    public List<CommentDTO> getComments(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + boardId));
        return board.getComments().stream()
                .map(CommentDTO::from)
                .collect(Collectors.toList());
    }

    /* 댓글 작성 */
    @Transactional
    public void writeComment(Long boardId, WriteCommentDTO writeCommentDTO) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + boardId));
        Comment newComment = new Comment(writeCommentDTO.getContent(), writeCommentDTO.getWriter(), board);
        commentRepository.save(newComment);
    }

    /* 댓글 수정 */
    @Transactional
    public void updateComment(Long commentId, WriteCommentDTO writeCommentDTO) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다. id=" + commentId));
        comment.update(writeCommentDTO);
        commentRepository.save(comment);
    }

    /* 댓글 삭제 */
    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다. id=" + commentId));
        commentRepository.delete(comment);
    }
}
