package com.embarkx.reviewms.controller;


import com.embarkx.reviewms.messaging.ReviewMessageProducer;
import com.embarkx.reviewms.model.Review;
import com.embarkx.reviewms.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/reviews")
public class ReviewController {

    private ReviewService reviewService;
    private ReviewMessageProducer reviewMessageProducer;

    public ReviewController(ReviewService reviewService,ReviewMessageProducer reviewMessageProducer) {
        this.reviewService = reviewService;
        this.reviewMessageProducer = reviewMessageProducer;
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews(@RequestParam Long companyId){
        return ResponseEntity.ok(reviewService.getAllReviews(companyId));
    }

    @PostMapping
    public ResponseEntity<String> addReview(@RequestBody Review newReview, @RequestParam Long companyId){
        boolean isReviewSaved = reviewService.addReview(newReview , companyId);
        if (isReviewSaved) {
            reviewMessageProducer.sendMessage(newReview);
            return new ResponseEntity<>("Review for company with id " + companyId + " added successfully.", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Review Not Saved",HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getReview(@PathVariable Long reviewId){
            return new ResponseEntity<>(reviewService.getReview(reviewId), HttpStatus.OK);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<String> updateReview(@RequestBody Review updatedReview , @PathVariable Long reviewId){
        boolean isReviewUpdated = reviewService.updateReviewById(updatedReview,reviewId);
        if (isReviewUpdated)
            return new ResponseEntity<>("Review updated successfully.", HttpStatus.OK);
        return new ResponseEntity<>("Review Not Updated",HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId){

        boolean isReviewDeleted = reviewService.deleteReviewById(reviewId);
        if (isReviewDeleted)
            return new ResponseEntity<>("Review deleted successfully.", HttpStatus.OK);
        return new ResponseEntity<>("Review Not Deleted",HttpStatus.NOT_FOUND);

    }
}
















