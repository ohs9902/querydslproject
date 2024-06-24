package com.sparta.redirect_outsourcing.domain.follow.repository;

import com.sparta.redirect_outsourcing.domain.follow.entity.Follow;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class FollowAdapter {
    public final FollowRepository followRepository;

    public void save(Follow follow) {
         followRepository.save(follow);
    }

    public List<Follow> findById(Long userId) {
        return followRepository.findAllByUserId(userId);
    }
}
