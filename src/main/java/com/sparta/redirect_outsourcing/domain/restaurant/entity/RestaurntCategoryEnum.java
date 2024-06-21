package com.sparta.redirect_outsourcing.domain.restaurant.entity;

public enum RestaurntCategoryEnum {
    KOREAN("한식"),
    CHINESE("중식"),
    JAPANESE("일식"),
    WESTERN("양식");

    private final String cuisineType;

    RestaurntCategoryEnum(String cuisineType) {
        this.cuisineType = cuisineType;
    }
}