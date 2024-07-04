package com.sparta.redirect_outsourcing.domain.review.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.redirect_outsourcing.domain.follow.entity.QFollow;
import com.sparta.redirect_outsourcing.domain.like.entity.QLike;
import com.sparta.redirect_outsourcing.domain.restaurant.entity.QRestaurant;
import com.sparta.redirect_outsourcing.domain.review.entity.QReview;
import com.sparta.redirect_outsourcing.domain.review.entity.Review;
import com.sparta.redirect_outsourcing.domain.user.entity.QUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReviewQueryDlsRepositoryImpl implements  ReviewQueryDslRepository{
    @Autowired
    private JPAQueryFactory queryFactory;

    public Page<Review> findByRestaurantFollow(Long restaurantId,Pageable pageable){
        QReview review = QReview.review;
        QFollow follow = QFollow.follow;
        QUser user = QUser.user;
        List<Review> reviews = queryFactory
                .selectFrom(review)
                .join(review.user,user)
                .join(follow).on(follow.user.eq(user))
                .where(follow.restaurant.id.eq(restaurantId))
                .orderBy(user.username.asc())
                .fetch();

        Long total = queryFactory
                .selectFrom(review)
                .join(review.user,user)
                .join(follow).on(follow.user.eq(user))
                .where(follow.restaurant.id.eq(restaurantId))
                .fetchCount();

        return new PageImpl<>(reviews,pageable,total);
    }

    @Override
    public Page<Review> findByLikeUser(Long userId , Pageable pageable) {
        QLike like = QLike.like;
        QReview review = QReview.review;

        List<Review> reviews = queryFactory.selectFrom(review)
                .join(review.likes,like)
                .where(like.user.id.eq(userId))
                .fetch();
        long total = queryFactory.selectFrom(review)
                .join(review.likes,like)
                .where(like.user.id.eq(userId))
                .fetchCount();

        return new PageImpl<>(reviews,pageable,total);
    }
}
