package com.sparta.redirect_outsourcing.domain.restaurant.dto.requestDto;

import com.sparta.redirect_outsourcing.domain.restaurant.entity.RestaurntCategoryEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class RestaurantUpdateRequestDto {
    @NotBlank
    private String name;

    @NotBlank
    private String address;

    @NotBlank
    private String category;

    @NotBlank
    private String description;
}
