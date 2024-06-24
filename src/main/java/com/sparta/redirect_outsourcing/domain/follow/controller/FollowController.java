package com.sparta.redirect_outsourcing.domain.follow.controller;

import com.sparta.redirect_outsourcing.auth.UserDetailsImpl;
import com.sparta.redirect_outsourcing.common.DataResponseDto;
import com.sparta.redirect_outsourcing.common.MessageResponseDto;
import com.sparta.redirect_outsourcing.domain.follow.dto.requestDto.FollowCreateRequestDto;
import com.sparta.redirect_outsourcing.domain.follow.dto.responseDto.FollowResponseDto;
import com.sparta.redirect_outsourcing.domain.follow.entity.Follow;
import com.sparta.redirect_outsourcing.domain.follow.service.FollowService;
import com.sparta.redirect_outsourcing.domain.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sparta.redirect_outsourcing.common.ResponseUtils.of;

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
        return followService.createFollow(restaurantId,user);

    }

//    @GetMapping("/users/{userId}/follows")
//    public ResponseEntity<DataResponseDto<List<FollowResponseDto>>> getFollows(
//            @PathVariable Long userId,
//            @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        User user = userDetails.getUser();
//        List<FollowResponseDto> follows = followService.getFollows(user);
//        return of(HttpStatus.OK, "찜목록을 조회합니다", follows);
//    }
//
//    @DeleteMapping("/restaurants/{restaurantId}/follows")
//    public ResponseEntity<MessageResponseDto> deleteFollow(
//            @PathVariable Long restaurantId,
//            @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        User user = userDetails.getUser();
//        followService.deleteFollow(user);
//        return of(HttpStatus.OK,"찜하기 취소")
//    }

}
