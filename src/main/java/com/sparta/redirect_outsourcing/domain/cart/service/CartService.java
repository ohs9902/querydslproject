package com.sparta.redirect_outsourcing.domain.cart.service;

import com.sparta.redirect_outsourcing.domain.cart.dto.CartItemRequestDto;
import com.sparta.redirect_outsourcing.domain.cart.dto.CartItemResponseDto;
import com.sparta.redirect_outsourcing.domain.cart.dto.CartRequestDto;
import com.sparta.redirect_outsourcing.domain.cart.dto.CartResponseDto;
import com.sparta.redirect_outsourcing.domain.cart.entity.Cart;
import com.sparta.redirect_outsourcing.domain.cart.entity.CartItem;
import com.sparta.redirect_outsourcing.domain.cart.repository.CartItemRepository;
import com.sparta.redirect_outsourcing.domain.cart.repository.CartRepository;
import com.sparta.redirect_outsourcing.domain.menu.entity.Menu;
import com.sparta.redirect_outsourcing.domain.restaurant.entity.Restaurant;
import com.sparta.redirect_outsourcing.domain.user.entity.User;
import com.sparta.redirect_outsourcing.domain.user.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {
// 검색 메서드(getCartItemsByUserId)(Long userId) - 특정 사용자의 장바구니 아이템을 조회하는 메서드
// 결제 메서드 장바구니에 담긴 아이템들을 주문으로 이관 혹은 결제하는 메서드 -> erd 확인

    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    /*
    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;
    */

    public CartItemResponseDto addItemToCart(CartItemRequestDto requestDto) {
       Cart cart = cartRepository.findById(requestDto.getCartId())
           .orElseThrow(() -> new IllegalArgumentException("Cart not found"));

//       Menu menu = menuRepository.findById(requestDto.getMenuId())
//           .orElseThrow(() -> new IllegalArgumentException("Menu not found"));

       CartItem cartItem = new CartItem(cart /*menu*/, requestDto.getQuantity(), requestDto.getQuantityPrice());
       cartItem = cartItemRepository.save(cartItem);

       return toDto(cartItem);
    }

    public List<CartItemResponseDto> getartItems() {
        List<CartItem> cartItems = cartItemRepository.findAll();
        return cartItems.stream().map(this::toDto).collect(Collectors.toList());
    }

    public CartItemResponseDto updateCartItem(CartItemRequestDto requestDto) {
        CartItem cartItem = cartItemRepository.findById(requestDto.getId())
            .orElseThrow(() -> new IllegalArgumentException("Cart item now found"));

        Cart cart = cartRepository.findById(requestDto.getCartId())
            .orElseThrow(() -> new IllegalArgumentException("Cart not found"));

//        Menu menu = menuRepository.findById(requestDto.getMenuId())
//            .orElseThrow(() -> new IllegalArgumentException("Menu not found"));

        cartItem.update(cart /*menu*/, requestDto.getQuantity(), requestDto.getQuantityPrice());
        cartItem = cartItemRepository.save(cartItem);

        return toDto(cartItem);
    }

    public void deleteCartItems(List<Long> menuIds) {
        cartItemRepository.deleteAllByMenuId(menuIds);
    }

    private  CartItemResponseDto toDto(CartItem cartItem) {
        return new CartItemResponseDto(
            cartItem.getId(),
            cartItem.getCart().getId(),
            cartItem.getMenu().getId(),
            cartItem.getQuantity(),
            cartItem.getQuantityPrice()
        );
    }




}