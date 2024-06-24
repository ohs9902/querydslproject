package com.sparta.redirect_outsourcing.domain.order.repository;

import com.sparta.redirect_outsourcing.domain.order.dto.findUserCartMenusDto;
import com.sparta.redirect_outsourcing.domain.order.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "SELECT c.users_id AS usersId, " +
            "ci.id AS cart_item_id, " +
            "m.name AS menuName, " +
            "m.price AS price, " +
            "ci.quantity AS quantity, " +
            "r.name AS restaurantName, " +
            "c.id AS cartsId " +
            "FROM carts c " +
            "INNER JOIN cart_items ci ON c.id = ci.carts_id " +
            "INNER JOIN menus m ON ci.menus_id = m.id " +
            "LEFT JOIN restaurants r ON m.restaurants_id = r.id " +
            "WHERE c.users_id = :userId", nativeQuery = true)
    List<findUserCartMenusDto> findUserCartMenus(@Param("userId") Long userId);

    Page<Order> findAllByUserId(Long userId, Pageable pageable);
}