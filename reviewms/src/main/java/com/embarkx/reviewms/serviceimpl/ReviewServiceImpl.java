package com.embarkx.reviewms.serviceimpl;


import com.embarkx.reviewms.model.Review;
import com.embarkx.reviewms.repository.ReviewRepository;
import com.embarkx.reviewms.service.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<Review> getAllReviews(Long companyId) {
        List<Review> reviews = reviewRepository.findByCompanyId(companyId);
        return reviews;
    }

    @Override
    public boolean addReview(Review newReview, Long companyId) {
        if (companyId != null && newReview != null){
            newReview.setCompanyId(companyId);
            reviewRepository.save(newReview);
            return true;
        }
        return false;
    }

    @Override
    public Review getReview(Long reviewId) {
        return reviewRepository.findById(reviewId).orElse(null);
    }

    @Override
    public boolean updateReviewById(Review updatedReview, Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElse(null);
        if ( review != null){
            review.setTitle(updatedReview.getTitle());
            review.setDescription(updatedReview.getDescription());
            review.setRating(updatedReview.getRating());
            review.setCompanyId(updatedReview.getCompanyId());
            reviewRepository.save(review);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteReviewById( Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElse(null);
        if ( review != null){
            reviewRepository.delete(review);
            return true;
        }
        return false;
    }

}
