package com.sparta.redirect_outsourcing.domain.menu.controller;

import com.sparta.redirect_outsourcing.auth.UserDetailsImpl;
import com.sparta.redirect_outsourcing.common.DataResponseDto;
import com.sparta.redirect_outsourcing.common.MessageResponseDto;
import com.sparta.redirect_outsourcing.common.ResponseUtils;
import com.sparta.redirect_outsourcing.domain.menu.dto.MenuRequestDto;
import com.sparta.redirect_outsourcing.domain.menu.dto.MenuResponseDto;
import com.sparta.redirect_outsourcing.domain.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;

    @PostMapping("/menus")
    public ResponseEntity<DataResponseDto<MenuResponseDto>> createMenu(
            @RequestBody MenuRequestDto requestDto ,
            @AuthenticationPrincipal UserDetailsImpl userDetails ){
        MenuResponseDto responseDto= menuService.createMenu(requestDto,userDetails.getUser());
        return ResponseUtils.of(HttpStatus.OK,"메뉴 등록 성공",responseDto);
    }

    @PutMapping("/menus/{menuId}")
    public ResponseEntity<DataResponseDto<MenuResponseDto>> updateMenu(
            @PathVariable Long menuId ,
            @RequestBody MenuRequestDto requestDto ,
            @AuthenticationPrincipal UserDetailsImpl userDetails
            ){
        MenuResponseDto responseDto = menuService.updateMenu(menuId,requestDto,userDetails.getUser());
        return ResponseUtils.of(HttpStatus.OK,"메뉴 수정 성공",responseDto);
    }

    @DeleteMapping("/menus/{menuId}")
    public ResponseEntity<MessageResponseDto> deleteMenu(
            @PathVariable Long menuId ,
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        menuService.deleteMenu(menuId,userDetails.getUser());

        return ResponseUtils.of(HttpStatus.OK,"메뉴 삭제 성공 ");
    }

    @GetMapping("/menus/{menuId}")
    public ResponseEntity<DataResponseDto<MenuResponseDto>> getSingleMenu(@PathVariable Long menuId){
        MenuResponseDto responseDto = menuService.getSingleMenu(menuId);
        return ResponseUtils.of(HttpStatus.OK,"메뉴 단일 조회 성공" , responseDto );
    }

    //식당 기능 병합후 수정 예정
    @GetMapping("/menus")
    public ResponseEntity<DataResponseDto<List<MenuResponseDto>>> getAllMenu(){
        List<MenuResponseDto> responseDtos = menuService.getAllMenu();
        return ResponseUtils.of(HttpStatus.OK,"메뉴 전체 조회 성공" , responseDtos );
    }
}
