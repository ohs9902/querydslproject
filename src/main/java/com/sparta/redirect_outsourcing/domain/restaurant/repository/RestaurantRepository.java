package com.sparta.redirect_outsourcing.domain.restaurant.repository;

import com.sparta.redirect_outsourcing.domain.restaurant.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {

}
