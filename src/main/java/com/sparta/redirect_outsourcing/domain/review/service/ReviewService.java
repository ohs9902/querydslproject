package com.sparta.redirect_outsourcing.domain.review.service;

import com.sparta.redirect_outsourcing.common.MessageResponseDto;
import com.sparta.redirect_outsourcing.common.ResponseCodeEnum;
import com.sparta.redirect_outsourcing.common.ResponseUtils;
import com.sparta.redirect_outsourcing.domain.review.dto.ReviewRequestDto;
import com.sparta.redirect_outsourcing.domain.review.entity.Review;
import com.sparta.redirect_outsourcing.domain.review.repository.ReviewAdapter;
import com.sparta.redirect_outsourcing.domain.review.repository.ReviewRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {
    private final ReviewAdapter reviewAdapter;
    private final ReviewRepository repository;
    public ReviewService(ReviewAdapter reviewAdapter, ReviewRepository repository) {
        this.reviewAdapter = reviewAdapter;
        this.repository = repository;
    }

    public ResponseEntity<MessageResponseDto> createReview(ReviewRequestDto requestDto){
        Long rating = requestDto.getRating();
        if(rating<1 || rating>5){
            return ResponseUtils.of(ResponseCodeEnum.REVIEW_OVER_RATING);
        }
        String comment = requestDto.getComment();
        Review review = new Review(rating,comment);
        repository.save(review);
        return ResponseUtils.of(HttpStatus.OK,"리뷰 등록 성공");
    }
}
