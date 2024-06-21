package com.sparta.redirect_outsourcing.exception.custom.review;

import com.sparta.redirect_outsourcing.common.ResponseCodeEnum;

public class ReviewOverRatingException extends ReviewException{
    public ReviewOverRatingException(ResponseCodeEnum responseCodeEnum) {
        super(responseCodeEnum);
    }
}
