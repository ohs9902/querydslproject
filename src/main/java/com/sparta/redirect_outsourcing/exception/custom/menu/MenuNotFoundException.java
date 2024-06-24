package com.sparta.redirect_outsourcing.exception.custom.menu;


import com.sparta.redirect_outsourcing.common.ResponseCodeEnum;

public class MenuNotFoundException extends MenuException {
    public MenuNotFoundException(ResponseCodeEnum responseCodeEnum) {
        super(responseCodeEnum);
    }
}
