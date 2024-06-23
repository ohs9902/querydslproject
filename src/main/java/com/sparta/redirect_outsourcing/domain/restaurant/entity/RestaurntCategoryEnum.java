package com.sparta.redirect_outsourcing.domain.restaurant.entity;

import com.sparta.redirect_outsourcing.common.ResponseCodeEnum;
import lombok.Getter;

@Getter
public enum RestaurntCategoryEnum {
    KOREAN("한식"),
    CHINESE("중식"),
    JAPANESE("일식"),
    WESTERN("양식");

    private final String cuisineType;

    RestaurntCategoryEnum(String cuisineType) {
        this.cuisineType = cuisineType;
    }

    public static String checkCategory(String requestCategory) {
        int count = 0;
        for(RestaurntCategoryEnum category : RestaurntCategoryEnum.values()){
            if(requestCategory.equals(category.getCuisineType())){
                requestCategory = category.name();
                return requestCategory;
            }

        }
            return null;
    }
}