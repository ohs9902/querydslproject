package com.sparta.redirect_outsourcing.domain.menu.repository;

import com.sparta.redirect_outsourcing.common.ResponseCodeEnum;
import com.sparta.redirect_outsourcing.domain.menu.entity.Menu;
import com.sparta.redirect_outsourcing.exception.custom.menu.MenuException;
import com.sparta.redirect_outsourcing.exception.custom.menu.MenuNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class MenuAdapter {
    private final MenuRepository menuRepository;

    public Menu save(Menu menu){
        return menuRepository.save(menu);
    }

    public Menu findById(Long id){
        return menuRepository.findById(id)
                .orElseThrow(()-> new MenuNotFoundException(ResponseCodeEnum.MENU_NOT_FOUND));
    }
    public List<Menu> findAll(){
        return menuRepository.findAll();
    }
    public void delete(Menu menu){
        menuRepository.delete(menu);
    }
}
