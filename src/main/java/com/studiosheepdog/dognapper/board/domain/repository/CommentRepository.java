package com.studiosheepdog.dognapper.board.domain.repository;

import com.studiosheepdog.dognapper.board.domain.aggregate.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
