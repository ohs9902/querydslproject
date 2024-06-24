package com.sparta.redirect_outsourcing.domain.follow.dto.responseDto;

import com.sparta.redirect_outsourcing.domain.follow.entity.Follow;
import lombok.Getter;
@Getter
public class FollowResponseDto
{
    String username;
    String restaurantName;

    public FollowResponseDto(Follow follow) {
        this.username=follow.getUser().getUsername();
        this.restaurantName = follow.getRestaurant().getName();
    }
}
