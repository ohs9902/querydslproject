package com.sparta.redirect_outsourcing.domain.review.entity;

import com.sparta.redirect_outsourcing.common.TimeStampEntity;
import com.sparta.redirect_outsourcing.domain.like.entity.Like;
import com.sparta.redirect_outsourcing.domain.restaurant.entity.Restaurant;
import com.sparta.redirect_outsourcing.domain.review.dto.ReviewRequestDto;
import com.sparta.redirect_outsourcing.domain.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Table(name = "reviews")
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
    @Setter
    @Column(nullable = false)
    private int likeCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurants_id")
    private Restaurant restaurant;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes = new ArrayList<>();

    public Review(Float rating, String comment, User user, Restaurant restaurant) {
        this.rating = rating;
        this.comment = comment;
        this.user = user;
        this.restaurant = restaurant;
        this.likeCount = 0;
    }

    public void update(ReviewRequestDto requestDto){
        this.rating = requestDto.getRating();
        this.comment = requestDto.getComment();
    }

    public void addLike(Like like){
        likes.add(like);
        like.setReview(this);
    }
}