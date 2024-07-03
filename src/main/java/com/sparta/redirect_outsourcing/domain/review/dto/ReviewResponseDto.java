package com.sparta.redirect_outsourcing.domain.review.dto;

import com.sparta.redirect_outsourcing.common.TimeStampEntity;
import com.sparta.redirect_outsourcing.domain.review.entity.Review;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ReviewResponseDto {
    private Float rating;
    private String comment;
    private int likeCount;
    private LocalDateTime createdAt;

    public static ReviewResponseDto of(Review review) {
        return ReviewResponseDto.builder()
                .rating(review.getRating())
                .comment(review.getComment())
                .likeCount(review.getLikeCount())
                .createdAt(review.getCreatedAt())
                .build();
    }
}