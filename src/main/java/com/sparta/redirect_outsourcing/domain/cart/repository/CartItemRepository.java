package com.sparta.redirect_outsourcing.domain.cart.repository;

import com.sparta.redirect_outsourcing.domain.cart.entity.Cart;
import com.sparta.redirect_outsourcing.domain.cart.entity.CartItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {


}
