package com.sparta.redirect_outsourcing.domain.user.controller;

import com.sparta.redirect_outsourcing.auth.UserDetailsImpl;
import com.sparta.redirect_outsourcing.common.DataResponseDto;
import com.sparta.redirect_outsourcing.common.MessageResponseDto;
import com.sparta.redirect_outsourcing.common.ResponseUtils;
import com.sparta.redirect_outsourcing.domain.user.dto.ProfileResponseDto;
import com.sparta.redirect_outsourcing.domain.user.dto.SignupRequestDto;
import com.sparta.redirect_outsourcing.domain.user.dto.UpdatePasswordRequestDto;
import com.sparta.redirect_outsourcing.domain.user.dto.UpdateProfileRequestDto;
import com.sparta.redirect_outsourcing.domain.user.entity.User;
import com.sparta.redirect_outsourcing.domain.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<MessageResponseDto> signup(@Validated @RequestBody SignupRequestDto requestDto) {
        userService.signup(requestDto);
        return ResponseUtils.of(HttpStatus.CREATED, "회원가입 성공");
    }

    // 비밀번호 변경
    @PutMapping("/password")
    public ResponseEntity<MessageResponseDto> updatePassword(@Validated @RequestBody UpdatePasswordRequestDto requestDto) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userDetails.getUser().getId();

        userService.updatePassword(userId, requestDto);
        return ResponseUtils.of(HttpStatus.OK, "비밀번호 변경 성공");
    }

    // 프로필 업로드
    @PutMapping("/profile")
    public ResponseEntity<MessageResponseDto> updateProfile(
            @Validated @RequestPart("updateProfileRequestDto") UpdateProfileRequestDto requestDto,
            @RequestPart(value = "profilePicture", required = false) MultipartFile profilePicture,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        userService.updateProfile(user, requestDto, profilePicture);
        return ResponseUtils.of(HttpStatus.OK, "프로필 수정 성공");
    }

    // 프로필 조회
    @GetMapping("/{userId}")
    public ResponseEntity<DataResponseDto<ProfileResponseDto>> getProfile(@PathVariable Long userId) {
        ProfileResponseDto profile = userService.getProfile(userId);
        return ResponseUtils.of(HttpStatus.OK, "프로필 조회 성공", profile);
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<MessageResponseDto> logout(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        userService.logout(userDetails.getUser());
        SecurityContextHolder.clearContext();
        return ResponseUtils.of(HttpStatus.OK, "로그아웃 성공");
    }

    // 회원 탈퇴
    @PutMapping("/withdraw")
    public ResponseEntity<MessageResponseDto> deleteUser(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        userService.deleteUser(user);
        SecurityContextHolder.clearContext();
        return ResponseUtils.of(HttpStatus.OK, "회원 탈퇴 성공");
    }
}