package com.sparta.redirect_outsourcing.domain.order.dto;

import com.sparta.redirect_outsourcing.domain.order.entity.Order;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class OrderResponseDto {
    private Long id;
    private String address;
    private String phoneNumber;
    private String restaurantName;
    private Long orderGroup;
    private int totalPrice;
    private List<GroupedMenuDto> groupedMenus;

    public static OrderResponseDto of(List<Order> orders, List<GroupedMenuDto> groupedMenus) {
        int totalPrice = groupedMenus.stream()
                .mapToInt(menu -> menu.getPrice() * menu.getQuantity())
                .sum();

        Order order = orders.get(0);
        return OrderResponseDto.builder()
                .address(order.getAddress())
                .phoneNumber(order.getPhoneNumber())
                .restaurantName(order.getRestaurantName())
                .orderGroup(order.getOrderGroup())
                .totalPrice(totalPrice)
                .groupedMenus(groupedMenus)
                .build();
    }
}