package com.sparta.redirect_outsourcing.domain.cart.repository;

import com.sparta.redirect_outsourcing.common.ResponseCodeEnum;
import com.sparta.redirect_outsourcing.domain.cart.entity.Cart;
import com.sparta.redirect_outsourcing.domain.cart.entity.CartItem;
import com.sparta.redirect_outsourcing.domain.user.entity.User;
import com.sparta.redirect_outsourcing.exception.custom.user.UserException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CartAdapter {
//    public User findById(Long id) {
//        return userRepository.findById(id)
//            .orElseThrow(() -> new UserException(ResponseCodeEnum.USER_NOT_FOUND));
//    }
//List<CartItem> findAllByUserId(Long userId);
//    void deleteAllByMenuIdIn(List<Long> menuIds);

}
