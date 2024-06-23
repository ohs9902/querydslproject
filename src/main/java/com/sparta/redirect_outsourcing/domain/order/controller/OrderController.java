package com.sparta.redirect_outsourcing.domain.order.controller;

import com.sparta.redirect_outsourcing.auth.UserDetailsImpl;
import com.sparta.redirect_outsourcing.common.DataResponseDto;
import com.sparta.redirect_outsourcing.common.MessageResponseDto;
import com.sparta.redirect_outsourcing.common.ResponseUtils;
import com.sparta.redirect_outsourcing.domain.order.dto.OrderCreateRequestDto;
import com.sparta.redirect_outsourcing.domain.order.dto.OrderResponseDto;
import com.sparta.redirect_outsourcing.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<DataResponseDto<OrderResponseDto>> createOrder(
            @AuthenticationPrincipal UserDetailsImpl loginUser,
            @RequestBody OrderCreateRequestDto requestDto
    ) {
        OrderResponseDto responseDto = orderService.createOrder(loginUser.getUser(), requestDto);
        return ResponseUtils.of(HttpStatus.OK, "주문 저장 성공", responseDto);
    }

    @GetMapping
    public ResponseEntity<DataResponseDto<Page<OrderResponseDto>>> findOrders(
            @AuthenticationPrincipal UserDetailsImpl loginUser,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) boolean isAsc
    ) {
        Page<OrderResponseDto> responseDtoList = orderService.findOrders(
                loginUser.getUser(), page-1, size, sortBy, isAsc
        );
        return ResponseUtils.of(HttpStatus.OK, "주문 전체 조회 성공", responseDtoList);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<DataResponseDto<OrderResponseDto>> findOrder(
            @AuthenticationPrincipal UserDetailsImpl loginUser,
            @PathVariable Long orderId
    ) {
        OrderResponseDto responseDto = orderService.findOrder(loginUser.getUser(), orderId);
        return ResponseUtils.of(HttpStatus.OK, "주문 단일 조회 성공", responseDto);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<MessageResponseDto> deleteOrder(
            @AuthenticationPrincipal UserDetailsImpl loginUser,
            @PathVariable Long orderId
    ) {
        orderService.deleteOrder(loginUser.getUser(), orderId);
        return ResponseUtils.of(HttpStatus.OK, "주문 삭제 성공");
    }
}