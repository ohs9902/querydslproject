package com.sparta.redirect_outsourcing.domain.user.service;

import com.sparta.redirect_outsourcing.domain.user.dto.ExamRequestDto;
import com.sparta.redirect_outsourcing.domain.user.dto.ExamResponseDto;
import com.sparta.redirect_outsourcing.domain.user.entity.User;
import com.sparta.redirect_outsourcing.domain.user.repository.UserAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserAdapter userAdapter;

    public ExamResponseDto testFindById(ExamRequestDto requestDto) {
        User user = userAdapter.findById(requestDto.getId());

        return ExamResponseDto.of(user);
    }
}