package com.sparta.redirect_outsourcing.domain.restaurant.service;

import com.sparta.redirect_outsourcing.common.ResponseCodeEnum;
import com.sparta.redirect_outsourcing.domain.restaurant.dto.requestDto.RestaurantCreateRequestDto;
import com.sparta.redirect_outsourcing.domain.restaurant.dto.requestDto.RestaurantUpdateRequestDto;
import com.sparta.redirect_outsourcing.domain.restaurant.dto.responseDto.RestaurantResponseDto;
import com.sparta.redirect_outsourcing.domain.restaurant.entity.Restaurant;
import com.sparta.redirect_outsourcing.domain.restaurant.repository.RestaurantAdapter;
import com.sparta.redirect_outsourcing.domain.restaurant.repository.RestaurantRepository;
import com.sparta.redirect_outsourcing.domain.user.entity.User;
import com.sparta.redirect_outsourcing.exception.custom.restaurant.RestaurantNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantAdapter restaurantAdapter;

    /************가게 등록*************/
    @Transactional
    public RestaurantResponseDto createRestaurant(RestaurantCreateRequestDto req) {
        Restaurant restaurant = new Restaurant(req);
        restaurantRepository.save(restaurant);
        return new RestaurantResponseDto(restaurant);

    }

    /************전체 가게 조회*************/
    public List<RestaurantResponseDto> getRestaurants() {
        return restaurantRepository.findAll().stream().map(RestaurantResponseDto::new).toList();
    }

    /************특정 가게 조회*************/
    public RestaurantResponseDto getOneRestaurant(Long restaurantId) {
        Restaurant restaurant = restaurantAdapter.findById(restaurantId);
        return new RestaurantResponseDto(restaurant);
    }

    /************가게 정보 변경*************/
    @Transactional
    public RestaurantResponseDto updateRestaurant(Long restaurantId, RestaurantUpdateRequestDto req, User user) {
        Restaurant restaurant = restaurantAdapter.findById(restaurantId);
        restaurant.verify(user);
        restaurant.update(req);
        return new RestaurantResponseDto(restaurant);

    }

    /************가게 삭제*************/
    @Transactional
    public RestaurantResponseDto deleteRestaurant(Long restaurantId,User user) {
        Restaurant restaurant = restaurantAdapter.findById(restaurantId);
        restaurant.verify(user);
        restaurantRepository.delete(restaurant);
        return new RestaurantResponseDto(restaurant);
    }
}
