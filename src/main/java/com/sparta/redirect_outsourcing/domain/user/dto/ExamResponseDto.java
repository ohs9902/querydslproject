package com.sparta.redirect_outsourcing.domain.user.dto;

import com.sparta.redirect_outsourcing.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExamResponseDto {
    private Long id;

    public static ExamResponseDto of(User user) {
        return ExamResponseDto.builder()
                .id(user.getId())
                .build();
    }
}