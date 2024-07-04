package com.sparta.redirect_outsourcing.domain.restaurant.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.redirect_outsourcing.domain.follow.entity.QFollow;
import com.sparta.redirect_outsourcing.domain.like.entity.QLike;
import com.sparta.redirect_outsourcing.domain.restaurant.entity.QRestaurant;
import com.sparta.redirect_outsourcing.domain.restaurant.entity.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.history.RevisionSort.asc;

@Repository
public class RestaurantQuerydslRepositoryImpl implements RestaurantQuerydslRepository{
    @Autowired
    private JPAQueryFactory queryFactory;

    public Page<Restaurant> findByFollowRestaurant(Long userId,Pageable pageable){
        QRestaurant restaurant = QRestaurant.restaurant;
        QFollow follow = QFollow.follow;

        List<Restaurant> restaurants = queryFactory
                .selectFrom(restaurant)
                .join(restaurant.follows,follow)
                .where(follow.restaurant.id.eq(restaurant.id))
                .orderBy(restaurant.user.username.asc())
                .fetch();

        Long total = queryFactory
                .selectFrom(restaurant)
                .join(restaurant.follows,follow)
                .where(follow.restaurant.id.eq(restaurant.id))
                .fetchCount();

        return new PageImpl<>(restaurants,pageable,total);
    }
    public Page<Restaurant> findByLikeRestaurant(Long userId, Pageable pageable){
        QRestaurant restaurant = QRestaurant.restaurant;
        QLike like = QLike.like;

        List<Restaurant> restaurants = queryFactory
                .selectFrom(restaurant)
                .join(restaurant.likes,like)
                .where(like.user.id.eq(userId))
                .fetch();

        long total = queryFactory
                .selectFrom(restaurant)
                .join(restaurant.likes,like)
                .where(like.user.id.eq(userId))
                .fetchCount();

        return new PageImpl<>(restaurants,pageable,total);

    }
}
