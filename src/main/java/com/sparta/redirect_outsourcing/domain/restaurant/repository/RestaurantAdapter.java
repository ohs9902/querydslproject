package com.sparta.redirect_outsourcing.domain.restaurant.repository;

import com.sparta.redirect_outsourcing.common.ResponseCodeEnum;
import com.sparta.redirect_outsourcing.domain.restaurant.dto.responseDto.RestaurantResponseDto;
import com.sparta.redirect_outsourcing.domain.restaurant.entity.Restaurant;
import com.sparta.redirect_outsourcing.exception.custom.restaurant.RestaurantNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RestaurantAdapter {

    private final RestaurantRepository restaurantRepository;

    public Restaurant findById(Long restaurantId) {
        return restaurantRepository.findById(restaurantId).orElseThrow(() -> new RestaurantNotFoundException(ResponseCodeEnum.RESTAURANT_NOT_FOUND));
    }

    public Restaurant save(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    public void delete(Restaurant restaurant) {
        restaurantRepository.delete(restaurant);
    }

    public List<Restaurant> findAll() {
        return restaurantRepository.findAll();
    }
}
