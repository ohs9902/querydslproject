package com.sparta.redirect_outsourcing.exception.custom.restaurant;

import com.sparta.redirect_outsourcing.common.ResponseCodeEnum;
import lombok.Getter;

@Getter
public class RestaurantException extends RuntimeException {
    private final ResponseCodeEnum responseCodeEnum;

    public RestaurantException(ResponseCodeEnum responseCodeEnum) {
        super(responseCodeEnum.getMessage());
        this.responseCodeEnum = responseCodeEnum;
    }
}
