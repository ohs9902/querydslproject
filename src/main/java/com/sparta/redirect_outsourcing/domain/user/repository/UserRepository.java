package com.sparta.redirect_outsourcing.domain.user.repository;

import com.sparta.redirect_outsourcing.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}