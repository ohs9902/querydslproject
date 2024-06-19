package com.sparta.redirect_outsourcing.exception.custom.review;

import com.sparta.redirect_outsourcing.common.ResponseCodeEnum;

public class ReviewOverRatingException extends ReviewException{
    private final ResponseCodeEnum responseCodeEnum;
    public ReviewOverRatingException(ResponseCodeEnum responseCodeEnum, ResponseCodeEnum responseCodeEnum1) {
        super(responseCodeEnum);
        this.responseCodeEnum = responseCodeEnum1;
    }
}
