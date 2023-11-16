package com.studiosheepdog.dognapper.board.domain.repository;

import com.studiosheepdog.dognapper.board.domain.aggregate.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
