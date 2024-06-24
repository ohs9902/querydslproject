package com.sparta.redirect_outsourcing.domain.follow.controller;

import com.sparta.redirect_outsourcing.auth.UserDetailsImpl;
import com.sparta.redirect_outsourcing.common.MessageResponseDto;
import com.sparta.redirect_outsourcing.common.ResponseUtils;
import com.sparta.redirect_outsourcing.domain.follow.service.FollowService;
import com.sparta.redirect_outsourcing.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@ResponseBody
@RequiredArgsConstructor
public class FollowController {

    public final FollowService followService;

    @PostMapping("/restaurants/{restaurantId}/follows")
    public ResponseEntity<MessageResponseDto> createFollow(
            @PathVariable Long restaurantId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        if (followService.toggleFollow(restaurantId, user)) {
            return ResponseUtils.of(HttpStatus.OK, "찜하기 추가");
        }
        return ResponseUtils.of(HttpStatus.OK, "찜하기 취소");
    }
}