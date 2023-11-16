package com.studiosheepdog.dognapper.board.application.service;

import com.studiosheepdog.dognapper.board.application.dto.WriteBoardDTO;
import com.studiosheepdog.dognapper.board.domain.aggregate.entity.Board;
import com.studiosheepdog.dognapper.board.domain.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    /* 게시글 작성 */
    public void writeBoard(WriteBoardDTO writeBoardDTO) {

        // 검증로직 추가하기

        // entity 변환
        Board newBoard = Board.builder()
                .title(writeBoardDTO.getTitle())
                .content(writeBoardDTO.getContent())
                .category(writeBoardDTO.getCategory())
                .writer(writeBoardDTO.getWriter())
                .build();

        boardRepository.save(newBoard);

    }
}
