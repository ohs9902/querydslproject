package com.sparta.redirect_outsourcing.domain.like.service;

import com.sparta.redirect_outsourcing.domain.like.dto.LikeResponseDto;
import com.sparta.redirect_outsourcing.domain.like.entity.Like;
import com.sparta.redirect_outsourcing.domain.like.repository.LikeAdapter;
import com.sparta.redirect_outsourcing.domain.restaurant.entity.Restaurant;
import com.sparta.redirect_outsourcing.domain.restaurant.repository.RestaurantAdapter;
import com.sparta.redirect_outsourcing.domain.review.entity.Review;
import com.sparta.redirect_outsourcing.domain.review.repository.ReviewAdapter;
import com.sparta.redirect_outsourcing.domain.user.entity.User;
import com.sparta.redirect_outsourcing.domain.user.repository.UserAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeAdapter likeAdapter;
    private final ReviewAdapter reviewAdapter;
    private final RestaurantAdapter restaurantAdapter;
    private final UserAdapter userAdapter;

    @Transactional
    public void addLike(Long userId, Long reviewId) {
        User user = userAdapter.findById(userId);
        Review review = reviewAdapter.findById(reviewId); // 이미 reviewAdapter에서 .orElsethrow처리 함

        // 동일한 리뷰에 여러 번 좋아요를 누를 수 없도록 예외 처리
        if (likeAdapter.existsByUserAndReview(user, review)) {
            throw new IllegalArgumentException("User has already liked this review");
        }
        Like like = new Like();
        like.setUser(user);
        like.setReview(review);

        review.setLikeCount(review.getLikeCount() + 1);

        Like savedLike = likeAdapter.saveLike(like);
    }

    @Transactional
    public void removeLike(Long userId, Long reviewId) {
        Review review = reviewAdapter.findById(reviewId);

        review.setLikeCount(review.getLikeCount() - 1);

        likeAdapter.deleteLike(userId, reviewId);
    }

    @Transactional(readOnly = true)
    public int countLikes(Long reviewId) {
        return likeAdapter.countLikes(reviewId);
    }

    @Transactional
    public void addLike2(Long userId, Long restaurantId) {
        User user = userAdapter.findById(userId);
        Restaurant restaurant = restaurantAdapter.findById(restaurantId);

        // 동일한 리뷰에 여러 번 좋아요를 누를 수 없도록 예외 처리
        if (likeAdapter.existByUserAndRestaurant(user, restaurant)) {
            throw new IllegalArgumentException("User has already liked this restaurant");
        }
        Like like = new Like();
        like.setUser(user);
        like.setRestaurant(restaurant);

        restaurant.setLikeCount(restaurant.getLikeCount() + 1);

        Like savedLike = likeAdapter.saveLike(like);
    }

    @Transactional
    public void removeLike2(Long userId, Long restaurantId) {
        Restaurant restaurant = restaurantAdapter.findById(restaurantId);

        restaurant.setLikeCount(restaurant.getLikeCount() - 1);

        likeAdapter.deleteLike2(userId, restaurantId);
    }

}