package com.sparta.redirect_outsourcing.domain.menu.dto;

import com.sparta.redirect_outsourcing.domain.menu.entity.Menu;
import com.sparta.redirect_outsourcing.domain.menu.entity.MenuCategoryEnum;
import com.sparta.redirect_outsourcing.domain.review.dto.ReviewResponseDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MenuResponseDto {
    private String name;
    private int price;
    private MenuCategoryEnum menuCategoryEnum;
    public static MenuResponseDto of(Menu menu){
        return MenuResponseDto.builder()
                .name(menu.getName())
                .price(menu.getPrice())
                .menuCategoryEnum(menu.getMenuCategoryEnum())
                .build();
    }


}
