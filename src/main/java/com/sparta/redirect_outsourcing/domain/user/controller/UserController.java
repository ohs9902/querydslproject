package com.sparta.redirect_outsourcing.domain.user.controller;

import com.sparta.redirect_outsourcing.common.DataResponseDto;
import com.sparta.redirect_outsourcing.common.MessageResponseDto;
import com.sparta.redirect_outsourcing.common.ResponseUtils;
import com.sparta.redirect_outsourcing.domain.user.dto.ExamRequestDto;
import com.sparta.redirect_outsourcing.domain.user.dto.ExamResponseDto;
import com.sparta.redirect_outsourcing.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<MessageResponseDto> test(
            @RequestBody ExamRequestDto requestDto
    ) {
        ExamResponseDto examResponseDto = userService.testFindById(requestDto);
        return ResponseUtils.of(HttpStatus.OK, "성공");
    }
}