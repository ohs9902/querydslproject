package com.sparta.redirect_outsourcing.domain.cart.service;

import com.sparta.redirect_outsourcing.domain.cart.dto.CartItemRequestDto;
import com.sparta.redirect_outsourcing.domain.cart.dto.CartItemResponseDto;
import com.sparta.redirect_outsourcing.domain.cart.dto.CartRequestDto;
import com.sparta.redirect_outsourcing.domain.cart.dto.CartResponseDto;
import com.sparta.redirect_outsourcing.domain.cart.entity.Cart;
import com.sparta.redirect_outsourcing.domain.cart.entity.CartItem;
import com.sparta.redirect_outsourcing.domain.cart.repository.CartItemRepository;
import com.sparta.redirect_outsourcing.domain.menu.entity.Menu;
import com.sparta.redirect_outsourcing.domain.restaurant.entity.Restaurant;
import com.sparta.redirect_outsourcing.domain.user.entity.User;
import com.sparta.redirect_outsourcing.domain.user.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {
// 검색 메서드(getCartItemsByUserId)(Long userId) - 특정 사용자의 장바구니 아이템을 조회하는 메서드
// 결제 메서드 장바구니에 담긴 아이템들을 주문으로 이관 혹은 결제하는 메서드 -> erd 확인

    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    /*private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository; */


}