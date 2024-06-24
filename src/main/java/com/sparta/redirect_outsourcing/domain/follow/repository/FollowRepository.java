package com.sparta.redirect_outsourcing.domain.follow.repository;

import com.sparta.redirect_outsourcing.domain.follow.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow,Long> {
    List<Follow> findAllByUserId(Long userId);
}
