package com.sparta.redirect_outsourcing.domain.menu.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import com.sparta.redirect_outsourcing.domain.menu.dto.MenuRequestDto;
import com.sparta.redirect_outsourcing.domain.menu.dto.MenuResponseDto;
import com.sparta.redirect_outsourcing.domain.menu.entity.Menu;
import com.sparta.redirect_outsourcing.domain.menu.entity.MenuCategoryEnum;
import com.sparta.redirect_outsourcing.domain.menu.repository.MenuAdapter;
import com.sparta.redirect_outsourcing.domain.restaurant.entity.Restaurant;
import com.sparta.redirect_outsourcing.domain.restaurant.repository.RestaurantAdapter;
import com.sparta.redirect_outsourcing.domain.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class MenuServiceTest {

    @Mock
    private MenuAdapter menuAdapter;

    @Mock
    private RestaurantAdapter restaurantAdapter;

    @InjectMocks
    private MenuService menuService;

    private User user;
    private Restaurant restaurant;
    private MenuRequestDto requestDto;
    private Menu menu;

    @BeforeEach
    public void setUp() {
        user = new User(); // User 객체를 실제 생성하거나 mock 객체로 대체 가능
        user.setId(1L);

        restaurant = new Restaurant(); // Restaurant 객체를 실제 생성하거나 mock 객체로 대체 가능
        restaurant.setId(1L);

        requestDto = new MenuRequestDto();
        requestDto.setName("Test Menu");
        requestDto.setPrice(1000);
        requestDto.setMenuCategory("메인");
        requestDto.setRestaurantId(1L);
        menu = new Menu("Test Menu", 1000, MenuCategoryEnum.MAIN, user, restaurant);
        menu.setId(1L);

    }

    @Test
    @Transactional
    public void createMenu_shouldCreateMenu() {
        when(restaurantAdapter.findById(1L)).thenReturn(restaurant);
        when(menuAdapter.save(any(Menu.class))).thenReturn(menu);

        MenuResponseDto responseDto = menuService.createMenu(requestDto, user);

        verify(restaurantAdapter, times(1)).findById(1L);
        verify(menuAdapter, times(1)).save(any(Menu.class));
        assertEquals("Test Menu", responseDto.getName());
        assertEquals("메인", responseDto.getMenuCategoryEnum().getCategory());
    }

    @Test
    @Transactional
    public void updateMenu_shouldUpdateMenu() {
        when(menuAdapter.findById(anyLong())).thenReturn(menu);

        MenuResponseDto responseDto = menuService.updateMenu(1L, requestDto, user);

        verify(menuAdapter, times(1)).findById(anyLong());
        assertEquals("Test Menu", responseDto.getName());
    }

    @Test
    @Transactional
    public void deleteMenu_shouldDeleteMenu() {
        when(menuAdapter.findById(anyLong())).thenReturn(menu);

        menuService.deleteMenu(1L, user);

        verify(menuAdapter, times(1)).findById(anyLong());
        verify(menuAdapter, times(1)).delete(any(Menu.class));
    }

    @Test
    @Transactional(readOnly = true)
    public void getSingleMenu_shouldReturnMenu() {
        when(menuAdapter.findById(anyLong())).thenReturn(menu);

        MenuResponseDto responseDto = menuService.getSingleMenu(1L);

        verify(menuAdapter, times(1)).findById(anyLong());
        assertEquals("Test Menu", responseDto.getName());
    }

    @Test
    @Transactional(readOnly = true)
    public void getAllMenu_shouldReturnAllMenus() {
        List<Menu> menus = new ArrayList<>();
        menus.add(menu);
        when(menuAdapter.findAllByRestaurantId(anyLong())).thenReturn(menus);

        List<MenuResponseDto> responseDtos = menuService.getAllMenu(1L);

        verify(menuAdapter, times(1)).findAllByRestaurantId(anyLong());
        assertEquals(1, responseDtos.size());
    }


}
