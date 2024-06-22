package com.sparta.redirect_outsourcing.common;

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
    INVALID_CURRENT_PASSWORD(HttpStatus.BAD_REQUEST, "현재 비밀번호가 유효하지 않습니다."),
    SAME_AS_OLD_PASSWORD(HttpStatus.BAD_REQUEST, "새 비밀번호는 이전 비밀번호와 다르게 설정해야 합니다."),
    UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "프로필 사진 업로드 중 오류가 발생했습니다."),
    // 주문
    CART_MENUS_NOT_FOUND(HttpStatus.NOT_FOUND, "장바구니의 메뉴를 찾을 수 없습니다."),
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 주문 내역을 찾을 수 없습니다."),

    // 리뷰
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND,"리뷰를 찾을 수 없습니다."),
    REVIEW_OVER_RATING(HttpStatus.BAD_REQUEST,"평점은 1에서5 사이 만 가능합니다."),


    // 장바구니
    CART_NOT_FOUND(HttpStatus.NOT_FOUND, "장바구니를 찾을 수 없습니다."),
    CART_ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "장바구니 목록을 찾을 수 없습니다.")
    ;
    private final HttpStatus httpStatus;
    private final String message;
}