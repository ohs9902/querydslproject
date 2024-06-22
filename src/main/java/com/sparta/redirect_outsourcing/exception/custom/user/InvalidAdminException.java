package com.sparta.redirect_outsourcing.exception.custom.user;

import com.sparta.redirect_outsourcing.common.ResponseCodeEnum;

public class InvalidAdminException extends UserException {
    public InvalidAdminException(ResponseCodeEnum responseCodeEnum) {
        super(responseCodeEnum);
    }
}