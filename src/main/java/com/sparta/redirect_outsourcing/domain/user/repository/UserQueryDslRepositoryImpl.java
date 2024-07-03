package com.sparta.redirect_outsourcing.domain.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.redirect_outsourcing.domain.like.entity.QLike;
import com.sparta.redirect_outsourcing.domain.restaurant.entity.QRestaurant;
import com.sparta.redirect_outsourcing.domain.review.entity.QReview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserQueryDslRepositoryImpl implements UserQueryDslRepository{
    @Autowired
    private JPAQueryFactory queryFactory;

    public Long findByRestaurantLikeCount(Long userId){
        QRestaurant restaurant = QRestaurant.restaurant;
        QLike like = QLike.like;
        return queryFactory
                .selectFrom(restaurant)
                .join(restaurant.likes,like)
                .where(like.user.id.eq(userId))
                .fetchCount();
    }

    public Long findByReviewLikeCount(Long userId){
        QReview review = QReview.review;
        QLike like = QLike.like;
        return queryFactory
                .selectFrom(review)
                .join(review.likes,like)
                .where(like.user.id.eq(userId))
                .fetchCount();
    }
}
