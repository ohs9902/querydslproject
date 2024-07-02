package com.sparta.redirect_outsourcing.domain.review.repository;

import com.sparta.redirect_outsourcing.domain.review.entity.Review;

import java.util.List;

public interface ReviewQueryDslRepository {
    List<Review> findByLikeUser(Long userId);
}
