package com.sparta.redirect_outsourcing.domain.review.repository;

import com.sparta.redirect_outsourcing.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
