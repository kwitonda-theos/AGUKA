package com.example.UBAKA.repository;

import com.example.UBAKA.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByEngineerId(Long engineerId);

    Optional<Review> findByJobId(Long jobId);
}