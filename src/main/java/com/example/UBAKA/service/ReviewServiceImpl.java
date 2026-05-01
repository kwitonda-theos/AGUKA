package com.example.UBAKA.service;

import com.example.UBAKA.model.Assignment;
import com.example.UBAKA.model.Customer;
import com.example.UBAKA.model.Engineer;
import com.example.UBAKA.model.Job;
import com.example.UBAKA.model.Review;
import com.example.UBAKA.model.enums.AssignmentStatus;
import com.example.UBAKA.model.enums.NotificationType;
import com.example.UBAKA.repository.AssignmentRepository;
import com.example.UBAKA.repository.CustomerRepository;
import com.example.UBAKA.repository.EngineerRepository;
import com.example.UBAKA.repository.JobRepository;
import com.example.UBAKA.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final JobRepository jobRepository;
    private final CustomerRepository customerRepository;
    private final EngineerRepository engineerRepository;
    private final AssignmentRepository assignmentRepository;
    private final NotificationService notificationService;

    public ReviewServiceImpl(ReviewRepository reviewRepository,
                             JobRepository jobRepository,
                             CustomerRepository customerRepository,
                             EngineerRepository engineerRepository,
                             AssignmentRepository assignmentRepository,
                             NotificationService notificationService) {
        this.reviewRepository = reviewRepository;
        this.jobRepository = jobRepository;
        this.customerRepository = customerRepository;
        this.engineerRepository = engineerRepository;
        this.assignmentRepository = assignmentRepository;
        this.notificationService = notificationService;
    }

    @Override
    @Transactional
    public Review createReview(Long jobId, Long customerId, Integer rating, String comment) {
        if (rating == null || rating < 1 || rating > 5) {
            throw new RuntimeException("Rating must be between 1 and 5");
        }

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Assignment assignment = assignmentRepository.findByJobId(jobId)
                .orElseThrow(() -> new RuntimeException("Assignment not found for this job"));

        if (assignment.getStatus() != AssignmentStatus.COMPLETED) {
            throw new RuntimeException("Only completed jobs can be reviewed");
        }

        if (reviewRepository.findByJobId(jobId).isPresent()) {
            throw new RuntimeException("Review already exists for this job");
        }

        Engineer engineer = assignment.getEngineer();

        Review review = new Review();
        review.setJob(job);
        review.setCustomer(customer);
        review.setEngineer(engineer);
        review.setRating(rating);
        review.setComment(comment);
        review.setCreatedAt(LocalDateTime.now());

        Review savedReview = reviewRepository.save(review);

        // Update Engineer Reputation
        double oldAverage = engineer.getAverageRating() != null ? engineer.getAverageRating() : 0.0;
        int totalReviews = engineer.getTotalReviews() != null ? engineer.getTotalReviews() : 0;

        double newAverage = ((oldAverage * totalReviews) + rating) / (totalReviews + 1);

        engineer.setAverageRating(newAverage);
        engineer.setTotalReviews(totalReviews + 1);

        engineerRepository.save(engineer);

        // Notify engineer
        notificationService.createNotification(
                engineer.getUser().getId(),
                "New Review Received",
                "You have received a new rating: " + rating + " stars",
                NotificationType.REVIEW_REQUEST
        );

        return savedReview;
    }

    @Override
    public List<Review> getReviewsByEngineer(Long engineerId) {
        return reviewRepository.findByEngineerId(engineerId);
    }

    @Override
    public Optional<Review> getReviewByJob(Long jobId) {
        return reviewRepository.findByJobId(jobId);
    }
}
