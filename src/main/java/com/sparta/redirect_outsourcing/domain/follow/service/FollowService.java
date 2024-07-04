package com.sparta.redirect_outsourcing.domain.follow.service;

import com.sparta.redirect_outsourcing.domain.follow.dto.responseDto.FollowResponseDto;
import com.sparta.redirect_outsourcing.domain.follow.entity.Follow;
import com.sparta.redirect_outsourcing.domain.follow.repository.FollowAdapter;
import com.sparta.redirect_outsourcing.domain.follow.repository.FollowQueryDslRepositoryImpl;
import com.sparta.redirect_outsourcing.domain.restaurant.entity.Restaurant;
import com.sparta.redirect_outsourcing.domain.restaurant.repository.RestaurantAdapter;
import com.sparta.redirect_outsourcing.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowService {
    public final FollowAdapter followAdapter;
    private final RestaurantAdapter restaurantAdapter;

    public boolean toggleFollow(Long restaurantId, User user) {
        //찜하기 대상 가게 불러오기
        Restaurant restaurant = restaurantAdapter.findById(restaurantId);

        Optional<Follow> findFollow = followAdapter.findByUserIdAndRestaurantId(user.getId(), restaurantId);
        // 이미 짐한 경우(DB에 찜하기 기록이 있음)
        if (findFollow.isPresent()) {
            followAdapter.delete(findFollow.get());
            return false;
        }
        // 찜하지 않았을 경우(DB에 찜하기 기록이 없음)
        else {
            followAdapter.save(new Follow(user, restaurant));
            return true;
        }
    }

    public List<FollowResponseDto> getFollows(User user) {
        return followAdapter.findById(user.getId()).stream().map(FollowResponseDto::new).toList();
    }
}