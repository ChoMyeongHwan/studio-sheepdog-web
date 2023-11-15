package com.studiosheepdog.dognapper.board.domain.repository;

import com.studiosheepdog.dognapper.board.domain.aggregate.entity.Borad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Borad, Long> {
}
