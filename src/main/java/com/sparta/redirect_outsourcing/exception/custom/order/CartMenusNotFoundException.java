package com.sparta.redirect_outsourcing.exception.custom.order;

import com.sparta.redirect_outsourcing.common.ResponseCodeEnum;

public class CartMenusNotFoundException extends OrderException {
    public CartMenusNotFoundException(ResponseCodeEnum responseCodeEnum) {
        super(responseCodeEnum);
    }
}