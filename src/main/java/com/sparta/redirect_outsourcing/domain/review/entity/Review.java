package com.sparta.redirect_outsourcing.domain.review.entity;

import com.sparta.redirect_outsourcing.common.TimeStampEntity;
import com.sparta.redirect_outsourcing.domain.review.dto.ReviewRequestDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
public class Review extends TimeStampEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(1)
    @Max(5)
    @Column(nullable = false)
    private Float rating;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    private int likeCount;

    //restaurant_id 추가예정 ManyToOne
    public Review(Float rating, String comment) {
        this.rating = rating;
        this.comment = comment;
        this.likeCount = 0;
    }

    public void update(ReviewRequestDto requestDto){
        this.rating = requestDto.getRating();
        this.comment = requestDto.getComment();
    }
}
