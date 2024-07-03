package com.sparta.redirect_outsourcing.domain.user.dto;

import com.sparta.redirect_outsourcing.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProfileResponseDto {
    private String nickname;
    private String introduce;
    private String pictureUrl;


    public void updateProfile(User user){
        this.nickname = user.getNickname();
        this.introduce = user.getIntroduce();
        this.pictureUrl = user.getPictureUrl();
    }
}