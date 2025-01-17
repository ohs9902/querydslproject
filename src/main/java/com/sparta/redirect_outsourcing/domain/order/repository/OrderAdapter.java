package com.sparta.redirect_outsourcing.domain.order.repository;

import com.sparta.redirect_outsourcing.common.ResponseCodeEnum;
import com.sparta.redirect_outsourcing.domain.order.dto.findUserCartMenusDto;
import com.sparta.redirect_outsourcing.domain.order.entity.Order;
import com.sparta.redirect_outsourcing.exception.custom.order.CartMenusNotFoundException;
import com.sparta.redirect_outsourcing.exception.custom.order.OrdersNotFoundException;
import com.sparta.redirect_outsourcing.exception.custom.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderAdapter {
    private final OrderRepository orderRepository;

    public List<findUserCartMenusDto> findUserCartMenus(Long userId) {
        List<findUserCartMenusDto> userCartMenus = orderRepository.findUserCartMenus(userId);
        if (userCartMenus.isEmpty()) {
            throw new CartMenusNotFoundException(ResponseCodeEnum.CART_MENUS_NOT_FOUND);
        }
        return userCartMenus;
    }

    public Order save(Order order) {
        return orderRepository.save(order);
    }

    public Page<Order> findAllByUserId(Long userId, Pageable pageable) {
        return orderRepository.findAllByUserId(userId, pageable);
    }

    public Order findById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrdersNotFoundException(ResponseCodeEnum.ORDER_NOT_FOUND));
    }

    public void deleteById(Long orderId) {
        orderRepository.deleteById(orderId);
    }
}