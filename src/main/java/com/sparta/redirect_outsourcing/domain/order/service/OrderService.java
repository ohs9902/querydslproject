package com.sparta.redirect_outsourcing.domain.order.service;

import com.sparta.redirect_outsourcing.auth.UserDetailsImpl;
import com.sparta.redirect_outsourcing.common.ResponseCodeEnum;
import com.sparta.redirect_outsourcing.domain.cart.repository.CartAdapter;
import com.sparta.redirect_outsourcing.domain.cart.repository.CartItemAdapter;
import com.sparta.redirect_outsourcing.domain.order.dto.OrderCreateRequestDto;
import com.sparta.redirect_outsourcing.domain.order.dto.OrderResponseDto;
import com.sparta.redirect_outsourcing.domain.order.dto.findUserCartMenusDto;
import com.sparta.redirect_outsourcing.domain.order.entity.Order;
import com.sparta.redirect_outsourcing.domain.order.entity.OrderDetail;
import com.sparta.redirect_outsourcing.domain.order.repository.OrderAdapter;
import com.sparta.redirect_outsourcing.domain.user.entity.User;
import com.sparta.redirect_outsourcing.exception.custom.user.UserNotMatchException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderAdapter orderAdapter;
    private final CartAdapter cartAdapter;
    private final CartItemAdapter cartItemAdapter;

    // 주문내역 저장. 장바구니에 담겨있는 메뉴들을 삭제 후 주문내역 저장
    @Transactional
    public OrderResponseDto createOrder(User loginUser, OrderCreateRequestDto requestDto) {
        // 카트에 담긴 메뉴들 조회
        List<findUserCartMenusDto> findUserCartMenus = orderAdapter.findUserCartMenus(loginUser.getId());

        Order order = Order.builder()
                .user(loginUser)
                .address(requestDto.getAddress())
                .phoneNumber(requestDto.getPhoneNumber())
                .restaurantName(findUserCartMenus.get(0).getRestaurantName())
                .build();
        List<OrderDetail> orderDetails = new ArrayList<>();
        int totalPrice = 0;
        for (findUserCartMenusDto menu : findUserCartMenus) {
            totalPrice += menu.getPrice() * menu.getQuantity();
            OrderDetail orderDetail = OrderDetail.builder()
                    .menuName(menu.getMenuName())
                    .price(menu.getPrice())
                    .quantity(menu.getQuantity())
                    .order(order)
                    .build();
            orderDetails.add(orderDetail);
        }
        order.setTotalPrice(totalPrice);
        order.setOrderDetails(orderDetails);
        Order savedOrder = orderAdapter.save(order);
        cartItemAdapter.deleteById(findUserCartMenus.get(0).getCartsId());
        cartAdapter.deleteByIdAndUserId(findUserCartMenus.get(0).getCartsId(), loginUser.getId());

        return OrderResponseDto.of(savedOrder);
    }

    // 주문내역 조회. (페이징)
    @Transactional(readOnly = true)
    public Page<OrderResponseDto> findOrders(User loginUser, int page, int size, String sortBy, boolean isAsc) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Order> findOrders = orderAdapter.findAllByUserId(loginUser.getId(), pageable);
        if (!Objects.equals(findOrders.getContent().get(0).getUser().getId(), loginUser.getId())) {
            throw new UserNotMatchException(ResponseCodeEnum.USER_NOT_MATCH);
        }

        return findOrders.map(OrderResponseDto::of);
    }

    // 주문내역 단일 조회
    @Transactional(readOnly = true)
    public OrderResponseDto findOrder(User loginUser, Long orderId) {
        Order findOrder = orderAdapter.findById(orderId);

        if (!Objects.equals(findOrder.getUser().getId(), loginUser.getId())) {
            throw new UserNotMatchException(ResponseCodeEnum.USER_NOT_MATCH);
        }

        return OrderResponseDto.of(findOrder);
    }

    @Transactional
    public void deleteOrder(User loginUser, Long orderId) {
        Order findOrder = orderAdapter.findById(orderId);

        if (!Objects.equals(findOrder.getUser().getId(), loginUser.getId())) {
            throw new UserNotMatchException(ResponseCodeEnum.USER_NOT_MATCH);
        }

        orderAdapter.deleteById(orderId);
    }
}