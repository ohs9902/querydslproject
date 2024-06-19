package com.sparta.redirect_outsourcing.domain.review.controller;

import com.sparta.redirect_outsourcing.common.MessageResponseDto;
import com.sparta.redirect_outsourcing.common.ResponseUtils;
import com.sparta.redirect_outsourcing.domain.review.dto.ReviewRequestDto;
import com.sparta.redirect_outsourcing.domain.review.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReviewController {
    private ReviewService reviewService;

    @PostMapping("/reviews")
    public ResponseEntity<MessageResponseDto> createReview(@RequestBody ReviewRequestDto requestDto){
        return reviewService.createReview(requestDto);
    }

}
