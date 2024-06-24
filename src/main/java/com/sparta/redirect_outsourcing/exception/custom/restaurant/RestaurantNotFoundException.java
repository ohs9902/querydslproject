package com.sparta.redirect_outsourcing.exception.custom.restaurant;

import com.sparta.redirect_outsourcing.common.ResponseCodeEnum;

public class RestaurantNotFoundException extends RuntimeException{
    private final ResponseCodeEnum responseCodeEnum;
    public RestaurantNotFoundException(ResponseCodeEnum responseCodeEnum) {
        super(responseCodeEnum.getMessage());
        this.responseCodeEnum = responseCodeEnum;
    }
}
