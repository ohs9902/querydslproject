package com.sparta.redirect_outsourcing.domain.follow.repository;

import com.sparta.redirect_outsourcing.domain.restaurant.entity.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FollowQueryDslRepository {
    Page<Restaurant> findByFollowRestaurant(Long userId, Pageable pageable);
}
