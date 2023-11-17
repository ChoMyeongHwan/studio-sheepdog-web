package com.studiosheepdog.dognapper.board.domain.service.valid;

import com.studiosheepdog.dognapper.board.application.dto.WriteBoardDTO;
import org.springframework.stereotype.Service;

@Service
public class BoardValidator {

    private static final int TITLE_MAX_LENGTH = 100;
    private static final int CONTENT_MAX_LENGTH = 1000;

    public void validateWriteBoardDTO(WriteBoardDTO writeBoardDTO) {
        if (writeBoardDTO == null) {
            throw new IllegalArgumentException("WriteBoardDTO는 null이 될 수 없습니다.");
        }
        if (writeBoardDTO.getTitle() == null || writeBoardDTO.getTitle().length() > TITLE_MAX_LENGTH) {
            throw new IllegalArgumentException("제목은 null이 될 수 없으며, " + TITLE_MAX_LENGTH + "자 이내여야 합니다.");
        }
        if (writeBoardDTO.getContent() == null || writeBoardDTO.getContent().length() > CONTENT_MAX_LENGTH) {
            throw new IllegalArgumentException("내용은 null이 될 수 없으며, " + CONTENT_MAX_LENGTH + "자 이내여야 합니다.");
        }
    }
}
