package com.sparta.redirect_outsourcing.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProfileLikeCountDto {
    private String nickname;
    private String introduce;
    private String pictureUrl;
    private Long restaurantLikeCount;
    private Long reviewLikeCount;
}
