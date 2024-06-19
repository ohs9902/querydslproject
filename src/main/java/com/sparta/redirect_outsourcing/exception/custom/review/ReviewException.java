package com.sparta.redirect_outsourcing.exception.custom.review;

import com.sparta.redirect_outsourcing.common.ResponseCodeEnum;
import lombok.Getter;

@Getter
public class ReviewException extends RuntimeException{
    private final ResponseCodeEnum responseCodeEnum;
    public ReviewException(ResponseCodeEnum responseCodeEnum){
        super(responseCodeEnum.getMessage());
        this.responseCodeEnum = responseCodeEnum;
    }
}
