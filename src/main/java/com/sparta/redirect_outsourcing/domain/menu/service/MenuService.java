package com.sparta.redirect_outsourcing.domain.menu.service;

import com.sparta.redirect_outsourcing.auth.UserDetailsImpl;
import com.sparta.redirect_outsourcing.common.ResponseCodeEnum;
import com.sparta.redirect_outsourcing.domain.menu.dto.MenuRequestDto;
import com.sparta.redirect_outsourcing.domain.menu.dto.MenuResponseDto;
import com.sparta.redirect_outsourcing.domain.menu.entity.Menu;
import com.sparta.redirect_outsourcing.domain.menu.entity.MenuCategoryEnum;
import com.sparta.redirect_outsourcing.domain.menu.repository.MenuAdapter;
import com.sparta.redirect_outsourcing.domain.user.entity.User;
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
    public MenuResponseDto createMenu(MenuRequestDto requestDto , UserDetailsImpl userDetails){
        MenuCategoryEnum menuCategoryEnum = findCategory(requestDto.getMenuCategory());

        if (menuCategoryEnum == null){
            throw new MenuException(ResponseCodeEnum.MENU_CATEGORY_NOT_FOUND);
        }
        User user = userDetails.getUser();
        Menu menu = new Menu(requestDto.getName(),requestDto.getPrice(),menuCategoryEnum , user);
        Menu savedMenu = menuAdapter.save(menu);
        return MenuResponseDto.of(savedMenu);
    }

    @Transactional
    public MenuResponseDto updateMenu(Long menuId,MenuRequestDto requestDto ,UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        Menu menu = menuAdapter.findById(menuId);
        if(menu.getUser().getId() != user.getId() ){
            throw new MenuException(ResponseCodeEnum.MENU_USER_NOT_MATCH);
        }
        MenuCategoryEnum menuCategoryEnum = findCategory(requestDto.getMenuCategory());
        if (menuCategoryEnum == null){
            throw new MenuException(ResponseCodeEnum.MENU_CATEGORY_NOT_FOUND);
        }
        menu.update(requestDto.getName(),requestDto.getPrice(),menuCategoryEnum);
        return MenuResponseDto.of(menu);
    }

    @Transactional
    public void deleteMenu(Long menuId , UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        Menu menu = menuAdapter.findById(menuId);

        if(menu.getUser().getId() != user.getId() ){
            throw new MenuException(ResponseCodeEnum.MENU_USER_NOT_MATCH);
        }
        menuAdapter.delete(menu);
    }

    //카테고리 검증
    public MenuCategoryEnum findCategory(String category){
        MenuCategoryEnum menuCategoryEnum = null;

        for (MenuCategoryEnum value : MenuCategoryEnum.values()) {
            if(category.equals(value.getCategory())){
                menuCategoryEnum = value;
                break;
            }
        }
        return menuCategoryEnum;
    }

}
