package com.sparta.redirect_outsourcing.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ResponseCodeEnum {
    // 유저
    LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "로그인 실패"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다"),
    USER_DELETED(HttpStatus.UNAUTHORIZED, "탈퇴한 사용자입니다"),
    INVALID_TOKENS(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    INVALID_ADMIN_TOKEN(HttpStatus.FORBIDDEN, "관리자 암호가 틀려 등록이 불가능합니다."),
    REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다.");
    // 주문

    // 리뷰




    private final HttpStatus httpStatus;
    private final String message;
}