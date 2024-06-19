package com.sparta.redirect_outsourcing.domain.review.entity;

import com.sparta.redirect_outsourcing.domain.review.dto.ReviewRequestDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(1)
    @Max(5)
    @Column(nullable = false)
    private Long rating;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    private int like_count = 0;

    @Column(nullable = false , updatable = false)
    private LocalDateTime create_at;

    @Column(nullable = false)
    private LocalDateTime update_at;

    //restaurant_id 추가예정 ManyToOne
    public Review(Long rating, String comment) {
        this.rating = rating;
        this.comment = comment;
    }
    public void update(ReviewRequestDto requestDto){
        this.rating = requestDto.getRating();
        this.comment = requestDto.getComment();
    }
    @PrePersist
    protected void onCreate(){
        create_at = LocalDateTime.now();
        update_at = LocalDateTime.now();
    }
    @PreUpdate
    protected  void onUpdate(){
        update_at = LocalDateTime.now();
    }

}
