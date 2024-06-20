package com.sparta.redirect_outsourcing.domain.review.controller;

import com.sparta.redirect_outsourcing.common.MessageResponseDto;
import com.sparta.redirect_outsourcing.common.ResponseCodeEnum;
import com.sparta.redirect_outsourcing.common.ResponseUtils;
import com.sparta.redirect_outsourcing.domain.review.dto.ReviewRequestDto;
import com.sparta.redirect_outsourcing.domain.review.dto.ReviewResponseDto;
import com.sparta.redirect_outsourcing.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/restaurants/reviews")
    public ResponseEntity<MessageResponseDto> createReview(@RequestBody ReviewRequestDto requestDto){
        if(requestDto.getRating()<1 || requestDto.getRating()>5){
            return ResponseUtils.of(ResponseCodeEnum.REVIEW_OVER_RATING);
        }

        ReviewResponseDto responseDto = reviewService.createReview(requestDto);

        return ResponseUtils.of(HttpStatus.OK, "리뷰 작성 성공");
    }
    /*
    @PostMapping
    public ResponseEntity<MessageResponseDto> test(
            @RequestBody ExamRequestDto requestDto
    ) {
        ExamResponseDto examResponseDto = userService.testFindById(requestDto);
        return ResponseUtils.of(HttpStatus.OK, "리뷰 작성 성공");
    }
     */

    @PutMapping("/restaurants/reviews/{reviewId}")
    public ResponseEntity<MessageResponseDto> updateReview(
            @RequestBody ReviewRequestDto requestDto , @PathVariable Long reviewId){
        if(requestDto.getRating()<1 || requestDto.getRating()>5){
            return ResponseUtils.of(ResponseCodeEnum.REVIEW_OVER_RATING);
        }
        ReviewResponseDto responseDto = reviewService.updateReview(requestDto,reviewId);
        return ResponseUtils.of(HttpStatus.OK,"리뷰 수정 성공");
    }

    @DeleteMapping("/restaurants/reviews/{reviewId}")
    public ResponseEntity<MessageResponseDto> deleteReview(@PathVariable Long reviewId){
        reviewService.deleteReview(reviewId);
        return ResponseUtils.of(HttpStatus.OK,"리뷰삭제 성공");
    }

    @GetMapping("/restaurants/{restaurantId}/reviews")
    public ResponseEntity<MessageResponseDto> getReviewList(){
        return null;
    }


}
