package com.sparta.redirect_outsourcing.domain.follow.service;

import com.sparta.redirect_outsourcing.common.MessageResponseDto;
import com.sparta.redirect_outsourcing.common.ResponseCodeEnum;
import com.sparta.redirect_outsourcing.domain.follow.dto.requestDto.FollowCreateRequestDto;
import com.sparta.redirect_outsourcing.domain.follow.dto.responseDto.FollowResponseDto;
import com.sparta.redirect_outsourcing.domain.follow.entity.Follow;
import com.sparta.redirect_outsourcing.domain.follow.repository.FollowAdapter;
import com.sparta.redirect_outsourcing.domain.follow.repository.FollowRepository;
import com.sparta.redirect_outsourcing.domain.restaurant.entity.Restaurant;
import com.sparta.redirect_outsourcing.domain.restaurant.repository.RestaurantAdapter;
import com.sparta.redirect_outsourcing.domain.restaurant.repository.RestaurantRepository;
import com.sparta.redirect_outsourcing.domain.user.entity.User;
import com.sparta.redirect_outsourcing.exception.custom.restaurant.RestaurantNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sparta.redirect_outsourcing.common.ResponseUtils.of;

@Service
@RequiredArgsConstructor
public class FollowService {

    public final FollowRepository followRepository;
    public final FollowAdapter followAdapter;
    private final RestaurantRepository restaurantRepository;
    private final RestaurantAdapter restaurantAdapter;

    public ResponseEntity<MessageResponseDto> createFollow(Long restaurantId, User user) {
        //찜하기 대상 가게 불러오기
        Restaurant restaurant = restaurantAdapter.findById(restaurantId);
        Follow follow = new Follow(user,restaurant);
        //유저의 찜목록 불러오기
        List<Follow> followsofUser= followAdapter.findById(user.getId());

        for (Follow f : followsofUser) {
            //user가 찜하고있는 가게가 없을 경우
            if (followsofUser == null) {
                break;
            }
            //이미 찜한 가게일 경우
            if (restaurant == f.getRestaurant()) {
                return of(ResponseCodeEnum.ALREADY_FOLLOWED);
            }
        }

        followAdapter.save(follow);
        return of(HttpStatus.OK, "찜하기 완료");

    }

}
