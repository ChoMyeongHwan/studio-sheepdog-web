package com.studiosheepdog.dognapper.board.application.dto;

import com.studiosheepdog.dognapper.board.domain.aggregate.entity.Like;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LikeDTO {
    private Long id;
    private String userId;

    public static LikeDTO from(Like like) {
        LikeDTO likeDTO = new LikeDTO();
        likeDTO.setId(like.getId());
        likeDTO.setUserId(like.getUserId());
        return likeDTO;
    }
}
