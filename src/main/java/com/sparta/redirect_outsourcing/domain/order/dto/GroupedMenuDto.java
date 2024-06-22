package com.sparta.redirect_outsourcing.domain.order.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class GroupedMenuDto {
    private String menuName;
    private int price;
    private int quantity;

    public static GroupedMenuDto of(String menuName, int price, int quantity) {
        return GroupedMenuDto.builder()
                .menuName(menuName)
                .price(price)
                .quantity(quantity)
                .build();
    }
}