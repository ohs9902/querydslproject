package com.sparta.redirect_outsourcing.exception.custom.user;

import com.sparta.redirect_outsourcing.common.ResponseCodeEnum;

public class TokensException extends UserException {
    public TokensException(ResponseCodeEnum responseCodeEnum) {
        super(responseCodeEnum);
    }
}