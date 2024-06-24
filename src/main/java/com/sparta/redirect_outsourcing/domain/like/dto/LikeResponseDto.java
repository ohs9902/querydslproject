package com.sparta.redirect_outsourcing.domain.like.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LikeResponseDto {

    private Long id;
    private Long reviewId;
    private Long userId;

    public LikeResponseDto(Long id, Long reviewId, Long userId) {
        this.id = id;
        this.reviewId = reviewId;
        this.userId = userId;
    }
}
