package com.sparta.redirect_outsourcing.domain.restaurant.entity;

import com.sparta.redirect_outsourcing.common.TimeStampEntity;
import com.sparta.redirect_outsourcing.domain.restaurant.dto.requestDto.RestaurantCreateRequestDto;
import com.sparta.redirect_outsourcing.domain.restaurant.dto.requestDto.RestaurantUpdateRequestDto;
import com.sparta.redirect_outsourcing.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.util.ArrayList;
@Getter
@Entity
@NoArgsConstructor
public class Restaurant extends TimeStampEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "restaurant_id",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant_id",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Menu> menus = new ArrayList<>();

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Enumerated
    @Column(nullable = false)
    private Category category;

    private String description;

    public Restaurant(RestaurantCreateRequestDto createReq) {
        this.name = createReq.getName();
        this.address = createReq.getAddress();
        this.category = createReq.getCategory();
        this.description = createReq.getDescription();
    }


    public void update(RestaurantUpdateRequestDto updateReq) {
        this.name = updateReq.getName();
        this.address = updateReq.getAddress();
        this.category = updateReq.getCategory();
        this.description = updateReq.getDescription();
    }
}
