package com.sparta.redirect_outsourcing.domain.cart.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CartItemRequestDto {
    private Long cartId;
    private Long menuId;
    private Long quantity;
    private Long quantityPrice;

}
