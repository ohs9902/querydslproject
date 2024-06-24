package com.sparta.redirect_outsourcing.exception.custom.restaurant;

import com.sparta.redirect_outsourcing.common.ResponseCodeEnum;
import com.sparta.redirect_outsourcing.domain.user.entity.User;
import lombok.Getter;

@Getter
public class NotYourRestaurantException extends RuntimeException {
    private final ResponseCodeEnum responseCodeEnum;
    private final String message;
    public NotYourRestaurantException(ResponseCodeEnum responseCodeEnum,User user) {
        super(user.getUsername()+responseCodeEnum.getMessage());
        this.responseCodeEnum = responseCodeEnum;
        this.message = user.getUsername()+responseCodeEnum.getMessage();
    }
}
