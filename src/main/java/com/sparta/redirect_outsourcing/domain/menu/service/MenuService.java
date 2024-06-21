package com.sparta.redirect_outsourcing.domain.menu.service;

import com.sparta.redirect_outsourcing.common.ResponseCodeEnum;
import com.sparta.redirect_outsourcing.domain.menu.dto.MenuRequestDto;
import com.sparta.redirect_outsourcing.domain.menu.dto.MenuResponseDto;
import com.sparta.redirect_outsourcing.domain.menu.entity.Menu;
import com.sparta.redirect_outsourcing.domain.menu.entity.MenuCategoryEnum;
import com.sparta.redirect_outsourcing.domain.menu.repository.MenuAdapter;
import com.sparta.redirect_outsourcing.exception.custom.menu.MenuException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

import static java.rmi.server.LogStream.log;

@Service
@Slf4j
@RequiredArgsConstructor
public class MenuService {
    private final MenuAdapter menuAdapter;
    @Transactional
    public MenuResponseDto createMenu(MenuRequestDto requestDto){
        MenuCategoryEnum menuCategoryEnum = null;

        for (MenuCategoryEnum value : MenuCategoryEnum.values()) {
            log(String.valueOf(value));
            if(requestDto.getMenuCategory().equals(value.getCategory())){
                menuCategoryEnum = value;
                break;
            }
        }
        if (menuCategoryEnum == null){
            throw new MenuException(ResponseCodeEnum.MENU_CATEGORY_NOT_FOUND);
        }
        Menu menu = new Menu(requestDto.getName(),requestDto.getPrice(),menuCategoryEnum);
        Menu savedMenu = menuAdapter.save(menu);
        return MenuResponseDto.of(savedMenu);
    }

}
