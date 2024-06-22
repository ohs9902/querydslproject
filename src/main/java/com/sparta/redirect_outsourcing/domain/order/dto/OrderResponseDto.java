package com.sparta.redirect_outsourcing.domain.order.dto;

import com.sparta.redirect_outsourcing.domain.order.entity.Order;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class OrderResponseDto {
    private String address;
    private String phoneNumber;
    private String restaurantName;
    private int totalPrice;
    private LocalDateTime createdAt;

    public static OrderResponseDto of(Order order) {
        return OrderResponseDto.builder()
                .address(order.getAddress())
                .phoneNumber(order.getPhoneNumber())
                .restaurantName(order.getRestaurantName())
                .totalPrice(order.getTotalPrice())
                .createdAt(order.getCreatedAt())
                .build();
    }
}