package com.sparta.redirect_outsourcing.exception.custom.user;

import com.sparta.redirect_outsourcing.common.ResponseCodeEnum;
import lombok.Getter;

@Getter
public class UserException extends RuntimeException {
    private final ResponseCodeEnum responseCodeEnum;

    public UserException(ResponseCodeEnum responseCodeEnum) {
        super(responseCodeEnum.getMessage());
        this.responseCodeEnum = responseCodeEnum;
    }
}