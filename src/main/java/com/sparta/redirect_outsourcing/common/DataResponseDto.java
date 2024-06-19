package com.sparta.redirect_outsourcing.common;

import lombok.Getter;

@Getter
public class DataResponseDto<T> extends MessageResponseDto {
    protected final T data;

    DataResponseDto(Integer statusCode, String message, T data) {
        super(statusCode, message);
        this.data = data;
    }
}