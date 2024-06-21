package com.sparta.redirect_outsourcing.domain.user.service;

import com.sparta.redirect_outsourcing.common.ResponseCodeEnum;
import com.sparta.redirect_outsourcing.domain.user.dto.SignupRequestDto;
import com.sparta.redirect_outsourcing.domain.user.entity.User;
import com.sparta.redirect_outsourcing.domain.user.entity.UserRoleEnum;
import com.sparta.redirect_outsourcing.domain.user.entity.UserStatusEnum;
import com.sparta.redirect_outsourcing.domain.user.repository.UserAdapter;
import com.sparta.redirect_outsourcing.exception.custom.user.UserException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserAdapter userAdapter;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin-password}")
    private String adminPassword;

    // 회원가입
    @Transactional
    public void signup(SignupRequestDto requestDto) {
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        User user = new User();
        user.setUsername(requestDto.getUsername());
        user.setPassword(encodedPassword);
        user.setUserStatus(UserStatusEnum.STATUS_NORMAL);

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.ROLE_USER;
        if (requestDto.isAdmin()) {
            if (!adminPassword.equals(requestDto.getAdminPassword())) {
                throw new UserException(ResponseCodeEnum.INVALID_ADMIN_TOKEN);
            }
            role = UserRoleEnum.ROLE_ADMIN;
        }
        user.setUserRole(role);
        userAdapter.save(user);
    }
}