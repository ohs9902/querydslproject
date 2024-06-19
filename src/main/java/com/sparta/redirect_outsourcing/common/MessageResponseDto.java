package com.sparta.redirect_outsourcing.common;

import lombok.Getter;

@Getter
public class MessageResponseDto {
    protected Integer statusCode;
    protected String message;

    public MessageResponseDto(Integer statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}