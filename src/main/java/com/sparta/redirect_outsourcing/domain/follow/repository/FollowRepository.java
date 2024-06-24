package com.sparta.redirect_outsourcing.domain.follow.repository;

import com.sparta.redirect_outsourcing.domain.follow.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow,Long> {
}
