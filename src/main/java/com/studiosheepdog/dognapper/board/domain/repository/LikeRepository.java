package com.studiosheepdog.dognapper.board.domain.repository;

import com.studiosheepdog.dognapper.board.domain.aggregate.entity.Board;
import com.studiosheepdog.dognapper.board.domain.aggregate.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByBoardAndUserId(Board board, String userId);
}
