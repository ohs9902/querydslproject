package com.sparta.redirect_outsourcing.domain.restaurant.controller;

import com.sparta.redirect_outsourcing.auth.UserDetailsImpl;
import com.sparta.redirect_outsourcing.common.DataResponseDto;
import com.sparta.redirect_outsourcing.common.MessageResponseDto;
import com.sparta.redirect_outsourcing.domain.restaurant.dto.requestDto.RestaurantCreateRequestDto;
import com.sparta.redirect_outsourcing.domain.restaurant.dto.requestDto.RestaurantUpdateRequestDto;
import com.sparta.redirect_outsourcing.domain.restaurant.dto.responseDto.RestaurantResponseDto;
import com.sparta.redirect_outsourcing.domain.restaurant.entity.Restaurant;
import com.sparta.redirect_outsourcing.domain.restaurant.service.RestaurantService;
import com.sparta.redirect_outsourcing.domain.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sparta.redirect_outsourcing.common.ResponseUtils.of;

@Slf4j
@RestController
@ResponseBody
@RequiredArgsConstructor
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;


    /************가게 등록*************/
    @PostMapping
    public ResponseEntity<DataResponseDto<RestaurantResponseDto>> createRestaurant(
            @Valid @RequestBody RestaurantCreateRequestDto req,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        RestaurantResponseDto responseDto= restaurantService.createRestaurant(req,user);
        return of(HttpStatus.OK, "가게 등록이 완료되었습니다.", responseDto);
    }

    /************전체 가게 조회*************/
    @GetMapping
    public ResponseEntity<DataResponseDto<List<RestaurantResponseDto>>> getRestaurants() {
        List<RestaurantResponseDto> restaurants = restaurantService.getRestaurants();
        return of(HttpStatus.OK, "전체 가게를 조회합니다.", restaurants);
    }

    /************특정 가게 조회*************/
    @GetMapping("/{restaurantId}")
    public ResponseEntity<DataResponseDto<RestaurantResponseDto>> getOneRestaurant(
            @PathVariable Long restaurantId) {
        RestaurantResponseDto responseDto = restaurantService.getOneRestaurant(restaurantId);
        return of(HttpStatus.OK,responseDto.getName()+"를(을) 조회합니다.",responseDto);
    }

    @GetMapping("/likeRestaurants")
    public ResponseEntity<DataResponseDto<Page<RestaurantResponseDto>>> likeRestaurants(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam(defaultValue = "9") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction
    ){
        if (userDetails == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Pageable pageable = PageRequest.of(page,size,Sort.by(direction,sortBy));
        Page<RestaurantResponseDto> restaurants = restaurantService.getLikeRestaurants(userDetails.getUser(),pageable);

        return of(HttpStatus.OK,"좋아요한 식당 조회",restaurants);
    }

    /************가게 정보 변경*************/
    @PutMapping("/{restaurantId}")
    public ResponseEntity<DataResponseDto<RestaurantResponseDto>> updateRestaurant(
            @PathVariable Long restaurantId,
            @Valid @RequestBody RestaurantUpdateRequestDto updateReq,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        RestaurantResponseDto responseDto = restaurantService.updateRestaurant(restaurantId, updateReq,userDetails.getUser());
        return of(HttpStatus.OK, responseDto.getName() + "의 정보 변경이 완료되었습니다.",responseDto);
    }

    /************가게 삭제*************/
    @DeleteMapping("/{restaurantId}")
    public ResponseEntity<MessageResponseDto> deleteRestaurant(@PathVariable Long restaurantId,
                                                               @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        RestaurantResponseDto responseDto = restaurantService.deleteRestaurant(restaurantId,user);
        return of(HttpStatus.OK, responseDto.getName() + "(이)가 삭제되었습니다.");
    }

    @GetMapping("/follow")
    public ResponseEntity<DataResponseDto<Page<RestaurantResponseDto>>> getFollowRestaurant(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam(defaultValue = "9") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction
    ){
        Pageable pageable = PageRequest.of(page,size,Sort.by(direction,sortBy));
        Page<RestaurantResponseDto> restaurants = restaurantService.getFollowRestaurant(userDetails.getUser(),pageable);
        return of(HttpStatus.OK,"팔로우한 식당 조회 성공",restaurants);
    }
}