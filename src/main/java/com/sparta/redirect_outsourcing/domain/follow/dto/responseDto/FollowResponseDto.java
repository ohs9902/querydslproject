package com.sparta.redirect_outsourcing.domain.follow.dto.responseDto;

import com.sparta.redirect_outsourcing.domain.follow.entity.Follow;
import lombok.Getter;
@Getter
public class FollowResponseDto
{
    String restaurantName;

    public FollowResponseDto(Follow follow) {
        this.restaurantName = follow.getRestaurant().getName();
    }
}
