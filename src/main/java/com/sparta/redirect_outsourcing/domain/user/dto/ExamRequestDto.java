package com.sparta.redirect_outsourcing.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ExamRequestDto {
    @NotBlank
    private Long id;
}