package com.sparta.redirect_outsourcing.common;

import com.sparta.redirect_outsourcing.domain.user.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ResponseCodeEnum {
    // 유저
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다"),
    USER_DELETED(HttpStatus.UNAUTHORIZED, "탈퇴한 사용자입니다"),
    INVALID_TOKENS(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    INVALID_ADMIN_TOKEN(HttpStatus.FORBIDDEN, "관리자 암호가 틀려 등록이 불가능합니다."),
    REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),

    //가게
    RESTAURANT_NOT_FOUND(HttpStatus.NOT_FOUND, "텅~ 가게를 찾을 수 없습니다."),
    NOT_YOUR_RESTAURANT(HttpStatus.UNAUTHORIZED,"님의 가게가 아닙니다."),
    NOT_EXIST_CATEGORY(HttpStatus.NOT_FOUND, "올바른 형식을 입력해주십시오."),

    // 주문

    // 리뷰
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND,"리뷰를 찾을 수 없습니다."),
    REVIEW_OVER_RATING(HttpStatus.BAD_REQUEST,"평점은 1에서5 사이만 가능합니다."),



    ; //고정

    private final HttpStatus httpStatus;
    private final String message;
}