package com.sparta.redirect_outsourcing.domain.review.repository;

import com.sparta.redirect_outsourcing.domain.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewQueryDslRepository {
    Page<Review> findByLikeUser(Long userId , Pageable pageable);
}
