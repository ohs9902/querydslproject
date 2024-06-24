package com.sparta.redirect_outsourcing.domain.like.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LikeRequestDto {

    private Long reviewId;

    public LikeRequestDto(Long reviewId) {
        this.reviewId = reviewId;
    }
}
