package com.sparta.redirect_outsourcing.domain.menu.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuRequestDto {
    private String name;
    private int price;
    private String menuCategory;
    private Long restaurantId;
}
