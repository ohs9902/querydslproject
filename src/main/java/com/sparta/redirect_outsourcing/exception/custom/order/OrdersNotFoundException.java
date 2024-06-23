package com.sparta.redirect_outsourcing.exception.custom.order;

import com.sparta.redirect_outsourcing.common.ResponseCodeEnum;

public class OrdersNotFoundException extends OrderException {
    public OrdersNotFoundException(ResponseCodeEnum responseCodeEnum) {
        super(responseCodeEnum);
    }
}