package com.sparta.redirect_outsourcing.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtils {

    // 에러 상황일 때 사용
    public static ResponseEntity<MessageResponseDto> of(ResponseCodeEnum responseCodeEnum) {
        return ResponseEntity.status(responseCodeEnum.getHttpStatus())
                .body(new MessageResponseDto(
                        responseCodeEnum.getHttpStatus().value(), responseCodeEnum.getMessage()
                ));
    }

    // 데이터 없이 상태코드와 메세지만 리턴할 때 사용
    public static ResponseEntity<MessageResponseDto> of(HttpStatus httpStatus, String message) {
        return ResponseEntity.status(httpStatus)
                .body(new MessageResponseDto(
                        httpStatus.value(), message
                ));
    }

    // 상태코드와 메세지와 데이터를 함께 리턴할 때 사용
    public static <T> ResponseEntity<DataResponseDto<T>> of(HttpStatus httpStatus, String message, T data) {
        return ResponseEntity.status(httpStatus)
                .body(new DataResponseDto<>(
                        httpStatus.value(), message, data
                ));
    }
}