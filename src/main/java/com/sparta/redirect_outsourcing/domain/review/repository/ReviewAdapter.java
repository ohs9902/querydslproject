package com.sparta.redirect_outsourcing.domain.review.repository;

import com.sparta.redirect_outsourcing.common.ResponseCodeEnum;
import com.sparta.redirect_outsourcing.domain.review.entity.Review;
import com.sparta.redirect_outsourcing.exception.custom.review.ReviewException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
@Slf4j
@Component
@RequiredArgsConstructor
public class ReviewAdapter {
    private ReviewRepository repository;
    public Review findById(Long id){
        return repository.findById(id)
                .orElseThrow(()-> new ReviewException(ResponseCodeEnum.REVIEW_NOT_FOUND));
    }
}
