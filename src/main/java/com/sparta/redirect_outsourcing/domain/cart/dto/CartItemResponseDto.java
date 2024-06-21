package com.sparta.redirect_outsourcing.domain.cart.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CartItemResponseDto {
    private Long id;
    private Long cartId;
    private Long menuId;
    private Long quantity;
    private Long quantityPrice;

    public CartItemResponseDto(Long id, Long id1, Long id2, Long quantity, Long quantityPrice) {
    }
}
