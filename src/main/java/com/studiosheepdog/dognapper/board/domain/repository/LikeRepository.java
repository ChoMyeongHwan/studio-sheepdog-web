package com.studiosheepdog.dognapper.board.domain.repository;

import com.studiosheepdog.dognapper.board.domain.aggregate.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
}
