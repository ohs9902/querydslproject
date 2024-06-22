package com.sparta.redirect_outsourcing.domain.order.controller;

import com.sparta.redirect_outsourcing.auth.UserDetailsImpl;
import com.sparta.redirect_outsourcing.common.DataResponseDto;
import com.sparta.redirect_outsourcing.common.MessageResponseDto;
import com.sparta.redirect_outsourcing.common.ResponseUtils;
import com.sparta.redirect_outsourcing.domain.cart.entity.Cart;
import com.sparta.redirect_outsourcing.domain.order.dto.OrderCreateRequestDto;
import com.sparta.redirect_outsourcing.domain.order.dto.OrderResponseDto;
import com.sparta.redirect_outsourcing.domain.order.repository.OrderRepository;
import com.sparta.redirect_outsourcing.domain.order.service.OrderService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<DataResponseDto<List<OrderResponseDto>>> createOrder(
            @AuthenticationPrincipal UserDetailsImpl loginUser,
            @RequestBody OrderCreateRequestDto requestDto
    ) {
        List<OrderResponseDto> responseDtoList = orderService.createOrder(requestDto, loginUser.getUser());
        return ResponseUtils.of(HttpStatus.OK, "주문 저장 성공", responseDtoList);
    }

    @GetMapping
    public ResponseEntity<DataResponseDto<List<OrderResponseDto>>> findOrders(
            @AuthenticationPrincipal UserDetailsImpl loginUser
    ) {
        // TODO orderGroup으로 묶어야 함
        List<OrderResponseDto> responseDtoList = orderService.findOrders(loginUser.getUser());
        return ResponseUtils.of(HttpStatus.OK, "주문 전체 조회 성공", responseDtoList);
    }

    @GetMapping("/{orderGroup}")
    public ResponseEntity<DataResponseDto<OrderResponseDto>> findOrder(
            @PathVariable Long orderGroup
    ) {
        OrderResponseDto responseDto = orderService.findOrder(orderGroup);
        return ResponseUtils.of(HttpStatus.OK, "주문 단일 조회 성공", responseDto);
    }

    @DeleteMapping("/{orderGroup}")
    public ResponseEntity<MessageResponseDto> deleteOrder(@PathVariable Long orderGroup) {
        orderService.deleteOrder(orderGroup);
        return ResponseUtils.of(HttpStatus.OK, "주문 삭제 성공");
    }
}