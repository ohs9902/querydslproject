package com.sparta.redirect_outsourcing.domain.review.service;

import com.sparta.redirect_outsourcing.common.ResponseCodeEnum;
import com.sparta.redirect_outsourcing.domain.like.repository.LikeAdapter;
import com.sparta.redirect_outsourcing.domain.restaurant.entity.Restaurant;
import com.sparta.redirect_outsourcing.domain.restaurant.repository.RestaurantAdapter;
import com.sparta.redirect_outsourcing.domain.review.dto.ReviewRequestDto;
import com.sparta.redirect_outsourcing.domain.review.dto.ReviewResponseDto;
import com.sparta.redirect_outsourcing.domain.review.entity.Review;
import com.sparta.redirect_outsourcing.domain.review.repository.ReviewAdapter;
import com.sparta.redirect_outsourcing.domain.review.repository.ReviewQueryDlsRepositoryImpl;
import com.sparta.redirect_outsourcing.domain.user.entity.User;
import com.sparta.redirect_outsourcing.exception.custom.review.ReviewOverRatingException;
import com.sparta.redirect_outsourcing.exception.custom.user.UserNotMatchException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewAdapter reviewAdapter;
    private final RestaurantAdapter restaurantAdapter;
    private final ReviewQueryDlsRepositoryImpl queryDlsRepository;
    private final LikeAdapter likeAdapter;

    @Transactional
    public ReviewResponseDto createReview(User user, ReviewRequestDto requestDto, Long restaurantsId){
        if(requestDto.getRating()<1 || requestDto.getRating()>5){
            throw new ReviewOverRatingException(ResponseCodeEnum.REVIEW_OVER_RATING);
        }

        Restaurant findRestaurant = restaurantAdapter.findById(restaurantsId);

        Review review = new Review(requestDto.getRating(), requestDto.getComment(), user, findRestaurant);
        Review savedReview = reviewAdapter.save(review);
        return ReviewResponseDto.of(savedReview);
    }

    @Transactional
    public ReviewResponseDto updateReview(User user, ReviewRequestDto requestDto , Long reviewId){
        if(requestDto.getRating()<1 || requestDto.getRating()>5){
            throw new ReviewOverRatingException(ResponseCodeEnum.REVIEW_OVER_RATING);
        }
        Review review = reviewAdapter.findById(reviewId);
        if (!Objects.equals(review.getUser().getId(), user.getId())){
            throw new UserNotMatchException(ResponseCodeEnum.USER_NOT_FOUND);
        }
        review.update(requestDto);
        return ReviewResponseDto.of(review);
    }

    @Transactional
    public void deleteReview(User user, Long reviewId){
        Review review = reviewAdapter.findById(reviewId);
        if (!Objects.equals(review.getUser().getId(), user.getId())){
            throw new UserNotMatchException(ResponseCodeEnum.USER_NOT_FOUND);
        }
        reviewAdapter.delete(review);
    }

    @Transactional(readOnly = true)
    public List<ReviewResponseDto> getReviews(Long restaurantsId){
        List<Review> reviews = reviewAdapter.findByRestaurantId(restaurantsId);
        List<ReviewResponseDto> responseReviews = new ArrayList<>();
        for (Review review : reviews) {
            responseReviews.add(ReviewResponseDto.of(review));
        }
        return responseReviews;
    }
    //querydsl
    @Transactional
    public Page<ReviewResponseDto> getLikeReviews(User user , Pageable pageable){
        Page<Review> reviewsPage = queryDlsRepository.findByLikeUser(user.getId(),pageable);
        return reviewsPage.map(review -> ReviewResponseDto.of(review));
    }

    @Transactional
    public Page<ReviewResponseDto> getFollowRestaurantReviews(Long restaurantId,Pageable pageable){
        Page<Review> reviewPage = queryDlsRepository.findByRestaurantFollow(restaurantId,pageable);
        return reviewPage.map(review -> ReviewResponseDto.of(review));
    }

}