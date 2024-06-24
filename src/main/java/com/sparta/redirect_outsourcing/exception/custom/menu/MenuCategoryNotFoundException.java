package com.sparta.redirect_outsourcing.exception.custom.menu;

import com.sparta.redirect_outsourcing.common.ResponseCodeEnum;

public class MenuCategoryNotFoundException extends MenuException {
    public MenuCategoryNotFoundException(ResponseCodeEnum responseCodeEnum) {
        super(responseCodeEnum);
    }
}
