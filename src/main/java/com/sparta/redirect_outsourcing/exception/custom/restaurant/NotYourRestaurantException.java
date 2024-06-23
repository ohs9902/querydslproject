package com.sparta.redirect_outsourcing.exception.custom.restaurant;

import com.sparta.redirect_outsourcing.common.ResponseCodeEnum;
import com.sparta.redirect_outsourcing.domain.user.entity.User;

public class NotYourRestaurantException extends RuntimeException {
    public NotYourRestaurantException(ResponseCodeEnum responseCodeEnum,User user) {
        super(user.getUsername()+responseCodeEnum.getMessage());
    }

}
