package com.sparta.redirect_outsourcing.domain.like.service;

import com.sparta.redirect_outsourcing.domain.like.dto.LikeResponseDto;
import com.sparta.redirect_outsourcing.domain.like.entity.Like;
import com.sparta.redirect_outsourcing.domain.like.repository.LikeAdapter;
import com.sparta.redirect_outsourcing.domain.review.entity.Review;
import com.sparta.redirect_outsourcing.domain.review.repository.ReviewAdapter;
import com.sparta.redirect_outsourcing.domain.user.entity.User;
import com.sparta.redirect_outsourcing.domain.user.repository.UserAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeAdapter likeAdapter;
    private final ReviewAdapter reviewAdapter;
    private final UserAdapter userAdapter;

    @Transactional
    public void addLike(Long userId, Long reviewId) {
        User user = userAdapter.findById(userId);
        Review review = reviewAdapter.findById(reviewId); // 이미 reviewAdapter에서 .orElsethrow처리 함

        // 동일한 리뷰에 여러 번 좋아요를 누를 수 없도록 예외 처리
        if (likeAdapter.existsByUserAndReview(user, review)) {
            throw new IllegalArgumentException("User has already liked this review");
        }
        Like like = new Like();
        like.setUser(user);
        like.setReview(review);

        Like savedLike = likeAdapter.saveLike(like);
    }

    @Transactional
    public void removeLike(Long userId, Long reviewId) {
        likeAdapter.deleteLike(userId, reviewId);
    }

    @Transactional(readOnly = true)
    public int countLikes(Long reviewId) {
        return likeAdapter.countLikes(reviewId);
    }


}
