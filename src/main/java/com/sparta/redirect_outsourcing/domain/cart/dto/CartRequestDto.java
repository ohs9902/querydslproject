package com.sparta.redirect_outsourcing.domain.cart.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CartRequestDto {
    @NotBlank
    private Long id;



}
