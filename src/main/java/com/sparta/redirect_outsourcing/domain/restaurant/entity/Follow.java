package com.sparta.redirect_outsourcing.domain.restaurant.entity;

import com.sparta.redirect_outsourcing.common.TimeStampEntity;
import com.sparta.redirect_outsourcing.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "follows")
public class Follow extends TimeStampEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
}
