package com.sparta.redirect_outsourcing.domain.user.controller;

import com.sparta.redirect_outsourcing.auth.UserDetailsImpl;
import com.sparta.redirect_outsourcing.common.MessageResponseDto;
import com.sparta.redirect_outsourcing.common.ResponseUtils;
import com.sparta.redirect_outsourcing.domain.user.dto.SignupRequestDto;
import com.sparta.redirect_outsourcing.domain.user.dto.UpdatePasswordRequestDto;
import com.sparta.redirect_outsourcing.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<MessageResponseDto> signup(@Validated @RequestBody SignupRequestDto requestDto) {
        userService.signup(requestDto);
        return ResponseUtils.of(HttpStatus.CREATED, "회원가입 성공");
    }
    @PutMapping("/password")
    public ResponseEntity<MessageResponseDto> updatePassword(@Validated @RequestBody UpdatePasswordRequestDto requestDto) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userDetails.getUser().getId();

        userService.updatePassword(userId, requestDto);
        return ResponseUtils.of(HttpStatus.OK, "비밀번호 변경 성공");
    }
}