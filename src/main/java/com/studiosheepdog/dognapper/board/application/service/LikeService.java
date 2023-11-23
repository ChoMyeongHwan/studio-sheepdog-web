package com.studiosheepdog.dognapper.board.application.service;

import com.studiosheepdog.dognapper.board.domain.aggregate.entity.Board;
import com.studiosheepdog.dognapper.board.domain.aggregate.entity.Like;
import com.studiosheepdog.dognapper.board.domain.repository.BoardRepository;
import com.studiosheepdog.dognapper.board.domain.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final BoardRepository boardRepository;
    private final LikeRepository likeRepository;

    /* 좋아요 조회 */
    @Transactional(readOnly = true)
    public int getLikes(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + boardId));
        return board.getLikeCount();
    }

    /* 좋아요 추가 */
    @Transactional
    public void addLike(Long boardId, Long userId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + boardId));

        String testUserId = "1"; // 임시 사용자 ID

        // 이미 좋아요를 누른 상태인 경우 요청을 거부한다.
        Optional<Like> existingLike = likeRepository.findByBoardAndUserId(board, testUserId);
        if (existingLike.isPresent()) {
            throw new IllegalArgumentException("이미 좋아요를 누른 상태입니다.");
        }

        Like like = new Like(testUserId, board);
        likeRepository.save(like);
    }

    /* 좋아요 취소 */
    @Transactional
    public void removeLike(Long likeId, Long userId) {
        Like like = likeRepository.findById(likeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 좋아요가 없습니다. likeId=" + likeId));
        likeRepository.delete(like);
    }
}
