package com.sparta.redirect_outsourcing.domain.restaurant.service;

import com.sparta.redirect_outsourcing.domain.restaurant.dto.requestDto.RestaurantCreateRequestDto;
import com.sparta.redirect_outsourcing.domain.restaurant.dto.requestDto.RestaurantUpdateRequestDto;
import com.sparta.redirect_outsourcing.domain.restaurant.dto.responseDto.RestaurantResponseDto;
import com.sparta.redirect_outsourcing.domain.restaurant.entity.Restaurant;
import com.sparta.redirect_outsourcing.domain.restaurant.repository.RestaurantAdapter;
import com.sparta.redirect_outsourcing.domain.restaurant.repository.RestaurantQuerydslRepository;
import com.sparta.redirect_outsourcing.domain.restaurant.repository.RestaurantRepository;
import com.sparta.redirect_outsourcing.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class RestaurantService {
    private final RestaurantAdapter restaurantAdapter;
    private final RestaurantQuerydslRepository querydslRepository;

    /************가게 등록*************/
    @Transactional
    public RestaurantResponseDto createRestaurant(RestaurantCreateRequestDto createReq, User user) {
        Restaurant restaurant = new Restaurant(createReq,user);
        restaurant = restaurantAdapter.save(restaurant);
        return new RestaurantResponseDto(restaurant);
    }

    /************전체 가게 조회*************/
    public List<RestaurantResponseDto> getRestaurants() {
        return restaurantAdapter.findAll().stream().map(RestaurantResponseDto::new).toList();
    }

    /************특정 가게 조회*************/
    public RestaurantResponseDto getOneRestaurant(Long restaurantId) {
        Restaurant restaurant = restaurantAdapter.findById(restaurantId);
        return new RestaurantResponseDto(restaurant);
    }

    /************가게 정보 변경*************/
    @Transactional
    public RestaurantResponseDto updateRestaurant(Long restaurantId, RestaurantUpdateRequestDto updateReq, User user) {
        Restaurant restaurant = restaurantAdapter.findById(restaurantId);
        restaurant.verify(user);
        restaurant.update(updateReq);
        return new RestaurantResponseDto(restaurant);

    }

    /************가게 삭제*************/
    @Transactional
    public RestaurantResponseDto deleteRestaurant(Long restaurantId,User user) {
        Restaurant restaurant = restaurantAdapter.findById(restaurantId);
        restaurant.verify(user);
        restaurantAdapter.delete(restaurant);
        return new RestaurantResponseDto(restaurant);
    }

    /************좋아요한 가게 조회*************/
    @Transactional
    public Page<RestaurantResponseDto> getLikeRestaurants(User user, Pageable pageable){
        Page<Restaurant> restaurantPage = querydslRepository.findByLikeRestaurant(user.getId(), pageable);
        return restaurantPage.map((restaurant) -> new RestaurantResponseDto(restaurant));
    }
}
