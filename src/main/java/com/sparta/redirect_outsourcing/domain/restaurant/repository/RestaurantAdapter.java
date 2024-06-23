package com.sparta.redirect_outsourcing.domain.restaurant.repository;

import com.sparta.redirect_outsourcing.common.ResponseCodeEnum;
import com.sparta.redirect_outsourcing.domain.restaurant.entity.Restaurant;
import com.sparta.redirect_outsourcing.exception.custom.restaurant.RestaurantNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RestaurantAdapter {

    private final RestaurantRepository restaurantRepository;

    public Restaurant findById(Long restaurantId) {
        return restaurantRepository.findById(restaurantId).orElseThrow(() -> new RestaurantNotFoundException(ResponseCodeEnum.RESTAURANT_NOT_FOUND));
    }
}
