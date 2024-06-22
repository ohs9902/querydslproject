package com.sparta.redirect_outsourcing.domain.order.repository;

import com.sparta.redirect_outsourcing.common.ResponseCodeEnum;
import com.sparta.redirect_outsourcing.domain.order.dto.findUserCartMenusDto;
import com.sparta.redirect_outsourcing.domain.order.entity.Order;
import com.sparta.redirect_outsourcing.exception.custom.order.CartMenusNotFoundException;
import com.sparta.redirect_outsourcing.exception.custom.order.OrdersNotFoundException;
import com.sparta.redirect_outsourcing.exception.custom.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    public List<Order> saveAll(List<Order> orders) {
        return orderRepository.saveAll(orders);
    }

    public List<Order> findByUserId(Long userId) {
        List<Order> orders = orderRepository.findByUserIdOrderByOrderGroupDesc(userId);
        if (orders.isEmpty()) {
            throw new UserNotFoundException(ResponseCodeEnum.USER_NOT_FOUND);
        }
        return orders;
    }

    public List<Order> findByOrderGroup(Long orderGroup) {
        List<Order> orders = orderRepository.findByOrderGroup(orderGroup);
        if (orders.isEmpty()) {
            throw new OrdersNotFoundException(ResponseCodeEnum.ORDER_NOT_FOUND);
        }
        return orders;
    }

    public void deleteCart(Long userId) {
//        cartRepository.deleteByUserId(userId);
    }

    public void deleteByOrderGroup(Long orderGroup) {
        orderRepository.deleteByOrderGroup(orderGroup);
    }
}