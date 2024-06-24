package com.sparta.redirect_outsourcing.domain.cart.controller;

import com.sparta.redirect_outsourcing.auth.UserDetailsImpl;
import com.sparta.redirect_outsourcing.common.DataResponseDto;
import com.sparta.redirect_outsourcing.common.MessageResponseDto;
import com.sparta.redirect_outsourcing.common.ResponseUtils;
import com.sparta.redirect_outsourcing.domain.cart.dto.CartItemRequestDto;
import com.sparta.redirect_outsourcing.domain.cart.dto.CartItemResponseDto;
import com.sparta.redirect_outsourcing.domain.cart.service.CartService;
import com.sparta.redirect_outsourcing.domain.user.entity.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;


    // 장바구니에 물건 추가
    @PostMapping("/carts")
    public ResponseEntity<DataResponseDto<CartItemResponseDto>> addItemToCart(
            @AuthenticationPrincipal UserDetailsImpl loginUser,
            @RequestBody CartItemRequestDto requestDto
    ) {
        CartItemResponseDto responseDto = cartService.addItemToCart(loginUser.getUser(), requestDto);
        return ResponseUtils.of(HttpStatus.OK, "장바구니 추가", responseDto);
    }

    // 장바구니 조회
    @GetMapping("/carts")
    public ResponseEntity<DataResponseDto<List<CartItemResponseDto>>> getCartItem(
            @AuthenticationPrincipal UserDetailsImpl loginUser
    ) {
        List<CartItemResponseDto> cartItems = cartService.getCartItems(loginUser.getUser());
        return ResponseUtils.of(HttpStatus.OK, "장바구니 조회 성공", cartItems);
    }

    // 장바구니 수정
    @PutMapping("/carts")
    public ResponseEntity<DataResponseDto<CartItemResponseDto>> updateCartItem(
            @AuthenticationPrincipal UserDetailsImpl loginUser,
            @RequestBody CartItemRequestDto requestDto
    ) {
        CartItemResponseDto responseDto = cartService.updateCartItem(loginUser.getUser(), requestDto);
        return ResponseUtils.of(HttpStatus.OK, "장바구니 수정 성공", responseDto);
    }

     // 장바구니 삭제 - 메뉴 만들어지면 사용 가능
    @DeleteMapping("/carts")
    public ResponseEntity<MessageResponseDto> deleteCartItems(
            @AuthenticationPrincipal UserDetailsImpl loginUser,
            @RequestParam List<Long> menuIds
    ) {
        System.out.println(menuIds);
        cartService.deleteCartItems(loginUser.getUser(), menuIds);
        return ResponseUtils.of(HttpStatus.OK, "장바구니 삭제 성공");
    }



}