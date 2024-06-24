package com.sparta.redirect_outsourcing.domain.cart.repository;

import com.sparta.redirect_outsourcing.domain.cart.entity.CartItem;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {

    @Modifying
    @Query("DELETE FROM CartItem ci WHERE ci.menu.id IN :menuIds")
    void deleteAllByMenuIdIn(@Param("menuIds") List<Long> menuIds);

    List<CartItem> findAllByCartId(Long cartId);

    Optional<CartItem> findByCartIdAndMenuId(Long cartId, Long menuId);

    void deleteByCartId(Long cartsId);
}