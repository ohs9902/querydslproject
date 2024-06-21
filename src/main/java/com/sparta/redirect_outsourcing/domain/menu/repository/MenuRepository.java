package com.sparta.redirect_outsourcing.domain.menu.repository;

import com.sparta.redirect_outsourcing.domain.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu,Long> {
}
