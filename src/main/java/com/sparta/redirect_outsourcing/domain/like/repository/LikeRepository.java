package com.sparta.redirect_outsourcing.domain.like.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.redirect_outsourcing.domain.like.entity.Like;
import com.sparta.redirect_outsourcing.domain.restaurant.entity.Restaurant;
import com.sparta.redirect_outsourcing.domain.review.entity.Review;
import com.sparta.redirect_outsourcing.domain.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    @Autowired
    JPAQueryFactory qureyFactory = null;

    int countByReviewId(Long reviewId);

    int countByRestaurantId(Long restaurantId);
    void deleteByUserIdAndReviewId(Long userId, Long reviewId);

    void deleteByUserIdAndRestaurantId(Long userId, Long restaurantId);
    boolean existsByUserAndReview(User user, Review review);

    boolean existsByUserAndRestaurant(User user, Restaurant restaurant);





}
