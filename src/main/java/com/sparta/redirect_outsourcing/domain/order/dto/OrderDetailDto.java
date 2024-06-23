package com.sparta.redirect_outsourcing.domain.order.dto;

import com.sparta.redirect_outsourcing.domain.order.entity.OrderDetail;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@ToString
public class OrderDetailDto {
    private String menuName;
    private int price;
    private int quantity;

    public static List<OrderDetailDto> of(List<OrderDetail> orderDetails) {
        return orderDetails.stream().map(OrderDetailDto::of).toList();
    }

    public static OrderDetailDto of(OrderDetail orderDetail) {
        return OrderDetailDto.builder()
                .menuName(orderDetail.getMenuName())
                .price(orderDetail.getPrice())
                .quantity(orderDetail.getQuantity())
                .build();
    }
}