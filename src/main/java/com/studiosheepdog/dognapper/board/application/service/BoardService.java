package com.studiosheepdog.dognapper.board.application.service;

import com.studiosheepdog.dognapper.board.application.dto.WriteBoardDTO;
import com.studiosheepdog.dognapper.board.domain.aggregate.entity.Board;
import com.studiosheepdog.dognapper.board.domain.repository.BoardRepository;
import com.studiosheepdog.dognapper.board.domain.service.valid.BoardValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardValidator boardValidator;

    /* 전체 조회 */
    public Page<Board> getAllBoards(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    /* 게시글 조회 */
    public Board getBoard(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
    }

    /* 게시글 작성 */
    public void writeBoard(WriteBoardDTO writeBoardDTO) {
        boardValidator.validateWriteBoardDTO(writeBoardDTO);
        Board newBoard = Board.from(writeBoardDTO);
        boardRepository.save(newBoard);
    }

    /* 게시글 수정 */
    public void updateBoard(Long id, WriteBoardDTO writeBoardDTO) {
        boardValidator.validateWriteBoardDTO(writeBoardDTO);
        Board board = getBoard(id);
        board.update(writeBoardDTO);
        boardRepository.save(board);
    }

    /* 게시글 삭제 */
    public void deleteBoard(Long id) {
        Board board = getBoard(id);
        boardRepository.delete(board);
    }

}
