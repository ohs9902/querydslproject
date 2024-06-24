package com.sparta.redirect_outsourcing.domain.cart.service;

import com.sparta.redirect_outsourcing.domain.cart.dto.CartItemRequestDto;
import com.sparta.redirect_outsourcing.domain.cart.dto.CartItemResponseDto;
import com.sparta.redirect_outsourcing.domain.cart.entity.Cart;
import com.sparta.redirect_outsourcing.domain.cart.entity.CartItem;
import com.sparta.redirect_outsourcing.domain.cart.repository.CartAdapter;
import com.sparta.redirect_outsourcing.domain.cart.repository.CartItemAdapter;
import com.sparta.redirect_outsourcing.domain.menu.entity.Menu;
import com.sparta.redirect_outsourcing.domain.menu.repository.MenuAdapter;
import com.sparta.redirect_outsourcing.domain.user.entity.User;
import com.sparta.redirect_outsourcing.domain.user.repository.UserAdapter;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartAdapter cartAdapter;
    private final CartItemAdapter cartItemAdapter;
    private final MenuAdapter menuAdapter;
    private final UserAdapter userAdapter;

    @Transactional
    public CartItemResponseDto addItemToCart(User loginUser, CartItemRequestDto requestDto) {

        Cart findCart = cartAdapter.findByUserId(loginUser.getId());
        Menu menu = menuAdapter.findById(requestDto.getMenusId());
        CartItem cartItem = new CartItem(
                findCart,
                menu,
                requestDto.getQuantity(),
                requestDto.getQuantity() * menu.getPrice()
        );
        CartItem savedCartItem = cartItemAdapter.save(cartItem);
        return new CartItemResponseDto(savedCartItem);
    }

    @Transactional(readOnly = true)
    public List<CartItemResponseDto> getCartItems(User user) {
        Cart findCart = cartAdapter.findByUserId(user.getId());
        List<CartItem> cartItems = cartItemAdapter.findAllByCartId(findCart.getId());
        findCart.setCartItems(cartItems);
        return cartItems.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Transactional
    public CartItemResponseDto updateCartItem(User user, CartItemRequestDto requestDto) {
        Cart findCart = cartAdapter.findByUserId(user.getId());
        CartItem findCartItems = cartItemAdapter.findByCartId(findCart.getId(), requestDto.getMenusId());
        Menu menu = menuAdapter.findById(requestDto.getMenusId());
        findCartItems.update(findCart, menu, requestDto.getQuantity(), requestDto.getQuantity() * menu.getPrice());
        cartItemAdapter.save(findCartItems);
        return toDto(findCartItems);
    }

    @Transactional
    public void deleteCartItems(User user, List<Long> menuIds) {
        // 여기는 확실하지 않아요. 한번 해봐야 알 것 같아요.
        Cart findCart = cartAdapter.findByUserId(user.getId());
        cartItemAdapter.deleteAllByMenuIdIn(menuIds);
    }

    private CartItemResponseDto toDto(CartItem cartItem) {
        return new CartItemResponseDto(
            cartItem.getId(),
            cartItem.getCart().getId(),
            cartItem.getMenu().getName(),
            cartItem.getQuantity(),
            cartItem.getQuantityPrice()
        );
    }
}