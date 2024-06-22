package com.sparta.redirect_outsourcing.domain.order.service;

import com.sparta.redirect_outsourcing.domain.order.dto.OrderCreateRequestDto;
import com.sparta.redirect_outsourcing.domain.order.dto.GroupedMenuDto;
import com.sparta.redirect_outsourcing.domain.order.dto.OrderResponseDto;
import com.sparta.redirect_outsourcing.domain.order.dto.findUserCartMenusDto;
import com.sparta.redirect_outsourcing.domain.order.entity.Order;
import com.sparta.redirect_outsourcing.domain.order.repository.OrderAdapter;
import com.sparta.redirect_outsourcing.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderAdapter orderAdapter;

    @Transactional
    public List<OrderResponseDto> createOrder(OrderCreateRequestDto requestDto, User user) {
        // 카트에 담긴 메뉴들 조회
        List<findUserCartMenusDto> userCartMenus = orderAdapter.findUserCartMenus(user.getId());

        List<Order> orders = new ArrayList<>();
        long orderGroup = System.currentTimeMillis();
        for (findUserCartMenusDto menu : userCartMenus) {
            Order order = Order.builder()
                    .user(user)
                    .address(requestDto.getAddress())
                    .phoneNumber(requestDto.getPhoneNumber())
                    .menuName(menu.getMenuName())
                    .price(menu.getPrice())
                    .quantity(menu.getQuantity())
                    .restaurantName(menu.getRestaurantName())
                    .orderGroup(orderGroup)
                    .build();
            orders.add(order);
        }
        List<Order> savedOrders = orderAdapter.saveAll(orders);
        Map<Long, List<Order>> groupedOrders = groupedOrdersByOrderGroupToMap(savedOrders);

//        TODO 카트 비우는 작업 해야됨
//        orderAdapter.deleteCart(user.getId());
        return groupedOrders.entrySet().stream()
                .map(entry -> createOrderResponseDto(entry.getValue()))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<OrderResponseDto> findOrders(User user) {
        List<Order> orders = orderAdapter.findByUserId(user.getId());

        Map<Long, List<Order>> groupedOrders = groupedOrdersByOrderGroupToMap(orders);

        return groupedOrders.entrySet().stream()
                .map(entry -> createOrderResponseDto(entry.getValue()))
                .toList();
    }

    @Transactional(readOnly = true)
    public OrderResponseDto findOrder(Long orderGroup) {
        List<Order> orders = orderAdapter.findByOrderGroup(orderGroup);

        List<GroupedMenuDto> groupedMenus = groupedMenusByOrderGroupToList(orders);

        return OrderResponseDto.of(orders, groupedMenus);
    }

    @Transactional
    public void deleteOrder(Long orderGroup) {
        orderAdapter.deleteByOrderGroup(orderGroup);
    }

    // orderGroup 기준으로 주문내역을 묶어 Map으로 반환
    // key == 주문내역의 order_group 값, values == 주문내역들
    private Map<Long, List<Order>> groupedOrdersByOrderGroupToMap(List<Order> savedOrders) {
        return savedOrders.stream()
                .collect(Collectors.groupingBy(
                        Order::getOrderGroup,
                        LinkedHashMap::new,
                        Collectors.toList()
                ));
    }

    // groupedOrdersByOrderGroupToMap()으로 묶은 주문내역들을 OrderResponseDto로 반환
    private OrderResponseDto createOrderResponseDto(List<Order> orders) {
        if (orders.isEmpty()) {
            return null;
        }

        List<GroupedMenuDto> groupedMenus = groupedMenusByOrderGroupToList(orders);

        return OrderResponseDto.of(orders, groupedMenus);
    }

    private List<GroupedMenuDto> groupedMenusByOrderGroupToList(List<Order> orders) {
        return orders.stream()
                .map(order -> GroupedMenuDto.of(order.getMenuName(), order.getPrice(), order.getQuantity()))
                .toList();
    }
}