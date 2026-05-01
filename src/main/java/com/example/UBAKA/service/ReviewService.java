package com.example.UBAKA.service;

import com.example.UBAKA.model.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewService {
    Review createReview(Long jobId, Long customerId, Integer rating, String comment);
    List<Review> getReviewsByEngineer(Long engineerId);
    Optional<Review> getReviewByJob(Long jobId);
}
