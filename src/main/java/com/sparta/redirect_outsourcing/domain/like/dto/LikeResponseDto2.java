package com.sparta.redirect_outsourcing.domain.like.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LikeResponseDto2 {
    private Long id;
    private Long restaurantId;
    private Long userId;

    public LikeResponseDto2(Long id, Long restaurantId, Long userId) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.userId = userId;
    }
}
