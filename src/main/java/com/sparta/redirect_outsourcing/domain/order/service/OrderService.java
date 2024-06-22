package com.sparta.redirect_outsourcing.domain.order.service;

import com.sparta.redirect_outsourcing.domain.cart.entity.Cart;
import com.sparta.redirect_outsourcing.domain.cart.repository.CartAdapter;
import com.sparta.redirect_outsourcing.domain.cart.repository.CartItemAdapter;
import com.sparta.redirect_outsourcing.domain.order.dto.OrderCreateRequestDto;
import com.sparta.redirect_outsourcing.domain.order.dto.GroupedMenuDto;
import com.sparta.redirect_outsourcing.domain.order.dto.OrderResponseDto;
import com.sparta.redirect_outsourcing.domain.order.dto.findUserCartMenusDto;
import com.sparta.redirect_outsourcing.domain.order.entity.Order;
import com.sparta.redirect_outsourcing.domain.order.entity.OrderDetail;
import com.sparta.redirect_outsourcing.domain.order.repository.OrderAdapter;
import com.sparta.redirect_outsourcing.domain.user.entity.User;
import jdk.swing.interop.SwingInterOpUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
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
    private final CartAdapter cartAdapter;
    private final CartItemAdapter cartItemAdapter;

    @Transactional
    public OrderResponseDto createOrder(OrderCreateRequestDto requestDto, User user) {
        // 카트에 담긴 메뉴들 조회
        List<findUserCartMenusDto> userCartMenus = orderAdapter.findUserCartMenus(user.getId());

        Order order = Order.builder()
                .user(user)
                .address(requestDto.getAddress())
                .phoneNumber(requestDto.getPhoneNumber())
                .restaurantName(userCartMenus.get(0).getRestaurantName())
                .build();
        List<OrderDetail> orderDetails = new ArrayList<>();
        int totalPrice = 0;
        for (findUserCartMenusDto menu : userCartMenus) {
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
        cartItemAdapter.deleteById(userCartMenus.get(0).getCartsId());
        cartAdapter.deleteByIdAndUserId(userCartMenus.get(0).getCartsId(), user.getId());

        return OrderResponseDto.of(savedOrder);
    }

    @Transactional(readOnly = true)
    public Page<OrderResponseDto> findOrders(User user, int page, int size, String sortBy, boolean isAsc) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        sortBy = sortBy != null ? sortBy : "createdAt";
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Order> orders = orderAdapter.findAllByUserId(user.getId(), pageable);
        return orders.map(OrderResponseDto::of);
    }

//    @Transactional(readOnly = true)
//    public OrderResponseDto findOrder(Long orderGroup) {
//        List<Order> orders = orderAdapter.findByOrderGroup(orderGroup);
//
//        List<GroupedMenuDto> groupedMenus = groupMenusByOrderGroupToList(orders);
//
//        return OrderResponseDto.of(orders, groupedMenus);
//    }
//
//    @Transactional
//    public void deleteOrder(Long orderGroup) {
//        orderAdapter.deleteByOrderGroup(orderGroup);
//    }
//
//    // orderGroup 기준으로 주문내역을 묶어 Map으로 반환
//    // key == 주문내역의 order_group 값, values == 주문내역들
//    private Map<Long, List<Order>> groupOrdersByOrderGroupToMap(List<Order> savedOrders) {
//        return savedOrders.stream()
//                .collect(Collectors.groupingBy(
//                        Order::getOrderGroup,
//                        LinkedHashMap::new,
//                        Collectors.toList()
//                ));
//    }
//
//    // groupedOrdersByOrderGroupToMap()으로 묶은 주문내역들을 OrderResponseDto로 반환
//    private OrderResponseDto createOrderResponseDto(List<Order> orders) {
//        if (orders.isEmpty()) {
//            return null;
//        }
//
//        List<GroupedMenuDto> groupedMenus = groupMenusByOrderGroupToList(orders);
//
//        return OrderResponseDto.of(orders, groupedMenus);
//    }
//
//    private List<GroupedMenuDto> groupMenusByOrderGroupToList(List<Order> orders) {
//        return orders.stream()
//                .map(order -> GroupedMenuDto.of(order.getMenuName(), order.getPrice(), order.getQuantity()))
//                .toList();
//    }
}