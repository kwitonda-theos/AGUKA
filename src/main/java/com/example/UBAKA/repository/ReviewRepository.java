package com.example.UBAKA.repository;

import com.example.UBAKA.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByEngineerId(Long engineerId);

    List<Review> findByCustomerId(Long customerId);

    List<Review> findByJobId(Long jobId);
}