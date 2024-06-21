package com.sparta.redirect_outsourcing.domain.menu.repository;

import com.sparta.redirect_outsourcing.common.ResponseCodeEnum;
import com.sparta.redirect_outsourcing.domain.menu.entity.Menu;
import com.sparta.redirect_outsourcing.exception.custom.menu.MenuException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
@Slf4j
@Component
@RequiredArgsConstructor
public class MenuAdapter {
    private final MenuRepository menuRepository;

    public Menu save(Menu menu){
        return menuRepository.save(menu);
    }

    public Menu findById(Long id){
        return menuRepository.findById(id).orElseThrow(()-> new MenuException(ResponseCodeEnum.MENU_NOT_FOUND));
    }
}
