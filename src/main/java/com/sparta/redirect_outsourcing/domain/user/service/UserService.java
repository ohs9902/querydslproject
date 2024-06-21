package com.sparta.redirect_outsourcing.domain.user.service;

import com.sparta.redirect_outsourcing.common.ResponseCodeEnum;
import com.sparta.redirect_outsourcing.common.S3Uploader;
import com.sparta.redirect_outsourcing.domain.user.dto.ProfileResponseDto;
import com.sparta.redirect_outsourcing.domain.user.dto.SignupRequestDto;
import com.sparta.redirect_outsourcing.domain.user.dto.UpdatePasswordRequestDto;
import com.sparta.redirect_outsourcing.domain.user.dto.UpdateProfileRequestDto;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserAdapter userAdapter;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin-password}")
    private String adminPassword;
    private final S3Uploader s3Uploader;


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

    // 비밀번호 변경
    @Transactional
    public void updatePassword(Long userId, UpdatePasswordRequestDto requestDto) {
        User user = userAdapter.findById(userId);

        // 현재 비밀번호 확인
        if (!passwordEncoder.matches(requestDto.getCurrentPassword(), user.getPassword())) {
            throw new UserException(ResponseCodeEnum.INVALID_CURRENT_PASSWORD);
        }

        // 새로운 비밀번호가 현재 비밀번호 및 최근 사용한 세 개의 비밀번호와 다른지 확인
        if (passwordEncoder.matches(requestDto.getNewPassword(), user.getPassword()) ||
                user.getPreviousPasswords().stream().anyMatch(pw -> passwordEncoder.matches(requestDto.getNewPassword(), pw))) {
            throw new UserException(ResponseCodeEnum.SAME_AS_OLD_PASSWORD);
        }

        // 비밀번호 업데이트
        String encodedNewPassword = passwordEncoder.encode(requestDto.getNewPassword());
        user.setPassword(encodedNewPassword);

        // 이전 비밀번호 업데이트 (최대 3개 유지)
        List<String> previousPasswords = user.getPreviousPasswords();
        if (previousPasswords.size() >= 3) {
            previousPasswords.remove(0);
        }
        previousPasswords.add(user.getPassword());
        user.setPreviousPasswords(previousPasswords);
        userAdapter.save(user);

    }


    // 프로필 수정
    @Transactional
    public void updateProfile(User user, UpdateProfileRequestDto requestDto, MultipartFile profilePicture) {
        user.setNickname(requestDto.getNickname());
        user.setIntroduce(requestDto.getIntroduce());

        if (profilePicture != null && !profilePicture.isEmpty()) {
            try {
                String pictureUrl = s3Uploader.upload(profilePicture, "profile-pictures");
                user.setPictureUrl(pictureUrl);
            } catch (IOException e) {
                throw new UserException(ResponseCodeEnum.UPLOAD_FAILED);
            }
        }
        userAdapter.save(user);
    }

    // 프로필 조회
    public ProfileResponseDto getProfile(Long userId) {
        User user = userAdapter.findById(userId);
        return new ProfileResponseDto(user.getNickname(), user.getIntroduce(), user.getPictureUrl());
    }

    // 로그아웃
    @Transactional
    public void logout(User user) {
        user.setRefreshToken(null);  // 리프레시 토큰을 무효화
        userAdapter.save(user);

    }

}