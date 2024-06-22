package com.sparta.redirect_outsourcing.exception.custom.user;

import com.sparta.redirect_outsourcing.common.ResponseCodeEnum;

public class PasswordException extends UserException {
    public PasswordException(ResponseCodeEnum responseCodeEnum) {
        super(responseCodeEnum);
    }
}