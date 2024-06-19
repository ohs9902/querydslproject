package com.sparta.redirect_outsourcing.exception.custom.order;

import com.sparta.redirect_outsourcing.common.ResponseCodeEnum;
import lombok.Getter;

@Getter
public class OrderException extends RuntimeException {
    private final ResponseCodeEnum responseCodeEnum;

    public OrderException(ResponseCodeEnum responseCodeEnum) {
        super(responseCodeEnum.getMessage());
        this.responseCodeEnum = responseCodeEnum;
    }
}