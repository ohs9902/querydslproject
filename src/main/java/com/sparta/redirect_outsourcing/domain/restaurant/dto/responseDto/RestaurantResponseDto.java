package com.sparta.redirect_outsourcing.domain.restaurant.dto.responseDto;

import com.sparta.redirect_outsourcing.domain.restaurant.entity.Restaurant;
import com.sparta.redirect_outsourcing.domain.restaurant.entity.RestaurntCategoryEnum;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RestaurantResponseDto {
    private String name;

    private String address;

    private RestaurntCategoryEnum categoryEnum;

    private String description;

    private LocalDateTime createdAt;

    public RestaurantResponseDto(Restaurant restaurant) {
        this.name = restaurant.getName();
        this.address = restaurant.getAddress();
        this.categoryEnum = restaurant.getCategory();
        this.description = restaurant.getDescription();
        this.createdAt = restaurant.getCreatedAt();

    }

}
