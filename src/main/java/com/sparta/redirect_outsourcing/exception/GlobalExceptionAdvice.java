package com.sparta.redirect_outsourcing.exception;

import com.sparta.redirect_outsourcing.common.MessageResponseDto;
import com.sparta.redirect_outsourcing.common.ResponseUtils;
import com.sparta.redirect_outsourcing.exception.custom.user.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<MessageResponseDto> handleUserException(UserException e) {
        log.error("에러 메세지: ", e);
        return ResponseUtils.of(e.getResponseCodeEnum());
    }
}