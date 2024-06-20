package com.sparta.redirect_outsourcing.domain.review.service;

import com.sparta.redirect_outsourcing.domain.review.dto.ReviewRequestDto;
import com.sparta.redirect_outsourcing.domain.review.dto.ReviewResponseDto;
import com.sparta.redirect_outsourcing.domain.review.entity.Review;
import com.sparta.redirect_outsourcing.domain.review.repository.ReviewAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewAdapter reviewAdapter;

    @Transactional
    public ReviewResponseDto createReview(ReviewRequestDto requestDto){
        Review review = new Review(requestDto.getRating(), requestDto.getComment());
        Review savedReview = reviewAdapter.save(review);
        log.info(savedReview.getComment());
        return ReviewResponseDto.of(savedReview);
    }

    @Transactional
    public ReviewResponseDto updateReview(ReviewRequestDto requestDto , Long reviewId){
        Review review = reviewAdapter.findById(reviewId);
        review.update(requestDto);
        Review updateReview = reviewAdapter.save(review);
        return ReviewResponseDto.of(updateReview);
    }

    @Transactional
    public void deleteReview(Long reviewId){
        Review review = reviewAdapter.findById(reviewId);
        reviewAdapter.delete(review);
    }

    //싣당 부분 완성후 수정할 예정
    @Transactional
    public List<ReviewResponseDto> getReviews(){
        List<Review> reviews = reviewAdapter.findAll();
        List<ReviewResponseDto> responseReviews = new ArrayList<>();
        for (Review review : reviews) {
            responseReviews.add(ReviewResponseDto.of(review));
        }
        return responseReviews;
    }

}
