package com.sparta.redirect_outsourcing.domain.review.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.redirect_outsourcing.domain.like.entity.QLike;
import com.sparta.redirect_outsourcing.domain.review.entity.QReview;
import com.sparta.redirect_outsourcing.domain.review.entity.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReviewQueryDlsRepositoryImpl implements  ReviewQueryDslRepository{
    @Autowired
    private JPAQueryFactory queryFactory;


    @Override
    public Page<Review> findByLikeUser(Long userId , Pageable pageable) {
        QLike like = QLike.like;
        QReview review = QReview.review;

        List<Review> reviews = queryFactory.selectFrom(review)
                .join(review.likes,like)
                .where(like.user.id.eq(userId))
                .fetch();
        long total = queryFactory.selectFrom(review)
                .join(review.likes,like)
                .where(like.user.id.eq(userId))
                .fetchCount();

        return new PageImpl<>(reviews,pageable,total);
    }
}
