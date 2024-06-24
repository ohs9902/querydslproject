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
    @Query("DELETE FROM CartItem ci WHERE ci.menu.id IN : menuIds")
    void deleteAllByMenuIdIn(List<Long> menuIds);

    // 이거는 확실하지 않음 쿼리 날려봐야 됨
    @Modifying
    @Query("DELETE FROM CartItem ci WHERE ci.cart.id = :cartId AND ci.menu.id IN : menuIds")
    void deleteAllByMenuIdIn(@Param("cartId") Long cartId, @Param("menuIds") List<Long> menuIds);

    List<CartItem> findAllByCartId(Long cartId);

    Optional<CartItem> findByCartId(Long cartId);
}