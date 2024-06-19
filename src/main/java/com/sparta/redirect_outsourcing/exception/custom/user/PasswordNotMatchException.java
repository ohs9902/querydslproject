package com.sparta.redirect_outsourcing.exception.custom.user;

import com.sparta.redirect_outsourcing.common.ResponseCodeEnum;

public class PasswordNotMatchException extends UserException {
    public PasswordNotMatchException(ResponseCodeEnum responseCodeEnum) {
        super(responseCodeEnum);
    }
}