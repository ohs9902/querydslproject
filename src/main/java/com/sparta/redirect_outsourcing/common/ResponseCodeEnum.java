package com.sparta.redirect_outsourcing.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ResponseCodeEnum {
    // 유저
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다"),

    // 주문

    // 리뷰
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND,"리뷰를 찾을 수 없습니다."),
    REVIEW_OVER_RATING(HttpStatus.BAD_REQUEST,"평점은 1에서5 사이 만 가능합니다.")


    ;
    private final HttpStatus httpStatus;
    private final String message;
}