package com.sparta.redirect_outsourcing.exception.custom.user;

import com.sparta.redirect_outsourcing.common.ResponseCodeEnum;

public class UserNotMatchException extends UserException {
    public UserNotMatchException(ResponseCodeEnum responseCodeEnum) {
        super(responseCodeEnum);
    }
}