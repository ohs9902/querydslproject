package com.sparta.redirect_outsourcing.domain.cart.dto;

import com.sparta.redirect_outsourcing.domain.cart.entity.CartItem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CartItemResponseDto {
    private Long id;
    private Long cartId;
//    private Long menusId;
    private String menuName;
    private Long quantity;
    private Long quantityPrice;

    public CartItemResponseDto(Long id, Long cartId, String menuName, Long quantity, Long quantityPrice) {
        this.id = id;
        this.cartId = cartId;
        this.menuName = menuName;
        this.quantity = quantity;
        this.quantityPrice = quantityPrice;
    }

    public CartItemResponseDto(CartItem cartItem) {
        this.id = cartItem.getId();
        this.cartId = cartItem.getCart().getId();
        this.menuName = cartItem.getMenu().getName();
        this.quantity = cartItem.getQuantity();
        this.quantityPrice = cartItem.getQuantityPrice();
    }
}