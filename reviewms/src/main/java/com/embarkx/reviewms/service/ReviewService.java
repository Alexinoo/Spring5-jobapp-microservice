package com.embarkx.reviewms.service;

import com.embarkx.reviewms.model.Review;

import java.util.List;

public interface ReviewService {
    List<Review> getAllReviews(Long companyId);
    boolean addReview(Review newReview , Long companyId);

    Review getReview(Long reviewId);

    boolean updateReviewById(Review updatedReview, Long reviewId);

    boolean deleteReviewById(Long reviewId);
}
