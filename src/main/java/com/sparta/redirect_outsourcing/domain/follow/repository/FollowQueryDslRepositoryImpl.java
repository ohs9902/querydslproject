package com.sparta.redirect_outsourcing.domain.follow.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FollowQueryDslRepositoryImpl {
    @Autowired
    private JPAQueryFactory queryFactory;



}
