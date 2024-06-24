package com.sparta.redirect_outsourcing.domain.follow.repository;

import com.sparta.redirect_outsourcing.domain.follow.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow,Long> {
    List<Follow> findAllByUserId(Long userId);

    Optional<Follow> findByUserIdAndRestaurantId(Long userId, Long restaurantId);
}