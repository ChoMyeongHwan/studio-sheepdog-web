package com.studiosheepdog.dognapper.board.application.service;

import com.studiosheepdog.dognapper.board.application.dto.BoardDTO;
import com.studiosheepdog.dognapper.board.application.dto.WriteBoardDTO;
import com.studiosheepdog.dognapper.board.domain.aggregate.entity.Board;
import com.studiosheepdog.dognapper.board.domain.repository.BoardRepository;
import com.studiosheepdog.dognapper.board.domain.service.valid.BoardValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardValidator boardValidator;

    /* 전체 조회 */
    @Transactional(readOnly = true)
    public Page<BoardDTO> getAllBoards(Pageable pageable) {
        return boardRepository.findAll(pageable)
                .map(BoardDTO::from);
    }

    /* 게시글 조회 */
    @Transactional(readOnly = true)
    public BoardDTO getBoard(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        return BoardDTO.from(board);
    }

    /* 게시글 작성 */
    @Transactional
    public void writeBoard(WriteBoardDTO writeBoardDTO) {
        boardValidator.validateWriteBoardDTO(writeBoardDTO);
        Board newBoard = Board.builder()
                .title(writeBoardDTO.getTitle())
                .content(writeBoardDTO.getContent())
                .category(writeBoardDTO.getCategory())
                .writer(writeBoardDTO.getWriter())
                .build();
        boardRepository.save(newBoard);
    }

    /* 게시글 수정 */
    @Transactional
    public void updateBoard(Long id, WriteBoardDTO writeBoardDTO) {
        boardValidator.validateWriteBoardDTO(writeBoardDTO);
        Optional<Board> board = boardRepository.findById(id);
        if (!board.isPresent()) {
            throw new IllegalArgumentException("해당 게시글이 없습니다. id=" + id);
        }
        board.get().update(writeBoardDTO);
        boardRepository.save(board.get());
    }

    /* 게시글 삭제 */
    @Transactional
    public void deleteBoard(Long id) {
        Optional<Board> board = boardRepository.findById(id);
        if (!board.isPresent()) {
            throw new IllegalArgumentException("해당 게시글이 없습니다. id=" + id);
        }
        boardRepository.delete(board.get());
    }
}
