package com.sparta.redirect_outsourcing.domain.restaurant.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.redirect_outsourcing.domain.like.entity.QLike;
import com.sparta.redirect_outsourcing.domain.restaurant.entity.QRestaurant;
import com.sparta.redirect_outsourcing.domain.restaurant.entity.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RestaurantQuerydslRepositoryImpl implements RestaurantQuerydslRepository{
    @Autowired
    private JPAQueryFactory queryFactory;
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
