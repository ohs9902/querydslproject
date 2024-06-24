package com.sparta.redirect_outsourcing.exception.custom.menu;

import com.sparta.redirect_outsourcing.common.ResponseCodeEnum;
import lombok.Getter;

@Getter
public class MenuException extends RuntimeException{
    private final ResponseCodeEnum responseCodeEnum;

    public MenuException(ResponseCodeEnum responseCodeEnum) {
        super(responseCodeEnum.getMessage());
        this.responseCodeEnum = responseCodeEnum;
    }
}
