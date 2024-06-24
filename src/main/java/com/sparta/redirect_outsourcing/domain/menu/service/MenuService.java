package com.sparta.redirect_outsourcing.domain.menu.service;

import com.sparta.redirect_outsourcing.auth.UserDetailsImpl;
import com.sparta.redirect_outsourcing.common.ResponseCodeEnum;
import com.sparta.redirect_outsourcing.domain.menu.dto.MenuRequestDto;
import com.sparta.redirect_outsourcing.domain.menu.dto.MenuResponseDto;
import com.sparta.redirect_outsourcing.domain.menu.entity.Menu;
import com.sparta.redirect_outsourcing.domain.menu.entity.MenuCategoryEnum;
import com.sparta.redirect_outsourcing.domain.menu.repository.MenuAdapter;
import com.sparta.redirect_outsourcing.domain.user.entity.User;
import com.sparta.redirect_outsourcing.exception.custom.menu.MenuCategoryNotFoundException;
import com.sparta.redirect_outsourcing.exception.custom.menu.MenuException;
import com.sparta.redirect_outsourcing.exception.custom.user.UserNotMatchException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
            throw new MenuCategoryNotFoundException(ResponseCodeEnum.MENU_CATEGORY_NOT_FOUND);
        }
        User user = userDetails.getUser();
        Menu menu = new Menu(requestDto.getName(),requestDto.getPrice(),menuCategoryEnum , user);
        Menu savedMenu = menuAdapter.save(menu);
        return MenuResponseDto.of(savedMenu);
    }

    @Transactional
    public MenuResponseDto updateMenu(Long menuId,MenuRequestDto requestDto ,User user){
        Menu menu = menuAdapter.findById(menuId);
        if(menu.getUser().getId() != user.getId() ){
            throw new UserNotMatchException(ResponseCodeEnum.MENU_USER_NOT_MATCH);
        }
        MenuCategoryEnum menuCategoryEnum = findCategory(requestDto.getMenuCategory());
        if (menuCategoryEnum == null){
            throw new MenuCategoryNotFoundException(ResponseCodeEnum.MENU_CATEGORY_NOT_FOUND);
        }
        menu.update(requestDto.getName(),requestDto.getPrice(),menuCategoryEnum);
        return MenuResponseDto.of(menu);
    }

    @Transactional
    public void deleteMenu(Long menuId , UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        Menu menu = menuAdapter.findById(menuId);

        if(menu.getUser().getId() != user.getId() ){
            throw new UserNotMatchException(ResponseCodeEnum.MENU_USER_NOT_MATCH);
        }
        menuAdapter.delete(menu);
    }

    @Transactional(readOnly = true)
    public MenuResponseDto getSingleMenu(Long menuId){
        Menu menu = menuAdapter.findById(menuId);
        return MenuResponseDto.of(menu);
    }

    //식당 기능 병합후 수정 예정
    @Transactional(readOnly = true)
    public List<MenuResponseDto> getAllMenu(){
        List<Menu> menus = menuAdapter.findAll();
        List<MenuResponseDto> responseDtos = new ArrayList<>();
        for (Menu menu : menus) {
            responseDtos.add(MenuResponseDto.of(menu));
        }

        return responseDtos;
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
