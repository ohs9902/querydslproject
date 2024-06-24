package com.sparta.redirect_outsourcing.exception.custom.user;

import com.sparta.redirect_outsourcing.common.ResponseCodeEnum;

public class ProfileImageUploadException extends UserException {
    public ProfileImageUploadException(ResponseCodeEnum responseCodeEnum) {
        super(responseCodeEnum);
    }
}