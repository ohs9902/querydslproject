package com.sparta.redirect_outsourcing.domain.order.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class OrderCreateRequestDto {
    @NotBlank(message = "주소를 입력해 주세요.")
    private String address;
    @NotBlank(message = "전화번호를 입력해 주세요.")
    private String phoneNumber;
}