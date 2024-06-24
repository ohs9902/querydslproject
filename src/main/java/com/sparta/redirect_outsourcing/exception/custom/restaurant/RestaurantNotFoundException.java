package com.sparta.redirect_outsourcing.exception.custom.restaurant;

import com.sparta.redirect_outsourcing.common.ResponseCodeEnum;

public class RestaurantNotFoundException extends RestaurantException {
    public RestaurantNotFoundException(ResponseCodeEnum responseCodeEnum) {
        super(responseCodeEnum);
    }
}
