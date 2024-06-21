package com.sparta.redirect_outsourcing.domain.menu.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MenuCategoryEnum {
    MAIN("메인"),
    SIDE("사이드"),
    DRINK("음료"),
    ;

    private final String category;
}