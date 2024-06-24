package com.sparta.redirect_outsourcing.domain.follow.repository;

import com.sparta.redirect_outsourcing.domain.follow.dto.responseDto.FollowResponseDto;
import com.sparta.redirect_outsourcing.domain.follow.entity.Follow;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class FollowAdapter {
    public final FollowRepository followRepository;

    public void save(Follow follow) {
         followRepository.save(follow);
    }

    public List<Follow> findById(Long userId) {
        return followRepository.findAllByUserId(userId);
    }

    public Optional<Follow> findByUserIdAndRestaurantId(Long userId, Long restaurantId) {
        return followRepository.findByUserIdAndRestaurantId(userId, restaurantId);
    }

    public void delete(Follow follow) {
        followRepository.delete(follow);
    }
}