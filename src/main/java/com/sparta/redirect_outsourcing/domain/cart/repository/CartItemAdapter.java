package com.sparta.redirect_outsourcing.domain.cart.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CartItemAdapter {
    private final CartItemRepository cartItemRepository;

    }

