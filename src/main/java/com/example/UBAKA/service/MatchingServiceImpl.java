package com.example.UBAKA.service;

import com.example.UBAKA.exception.ResourceNotFoundException;
import com.example.UBAKA.model.Engineer;
import com.example.UBAKA.model.Job;
import com.example.UBAKA.model.enums.JobStatus;
import com.example.UBAKA.model.enums.NotificationType;
import com.example.UBAKA.model.enums.VerificationStatus;
import com.example.UBAKA.repository.EngineerRepository;
import com.example.UBAKA.repository.JobRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of {@link MatchingService} that matches verified engineers
 * to jobs based on specialization, location, and rating.
 */
@Service
public class MatchingServiceImpl implements MatchingService {

    private final JobRepository jobRepository;
    private final EngineerRepository engineerRepository;
    private final NotificationService notificationService;

    public MatchingServiceImpl(JobRepository jobRepository,
                               EngineerRepository engineerRepository,
                               NotificationService notificationService) {
        this.jobRepository = jobRepository;
        this.engineerRepository = engineerRepository;
        this.notificationService = notificationService;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Algorithm:
     * <ol>
     *   <li>Fetch job by ID (throw if not found)</li>
     *   <li>Query all VERIFIED engineers with the matching specialization</li>
     *   <li>Sort results: same-location engineers first, then by highest rating</li>
     * </ol>
     */
    @Override
    @Transactional(readOnly = true)
    public List<Engineer> findMatchingEngineers(Long jobId) {
        // 1. Fetch the job
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job", jobId));

        String requiredSpecialization = job.getSpecializationRequired();
        String jobLocation = job.getLocation();

        // 2. Fetch verified engineers with matching specialization
        List<Engineer> candidates = engineerRepository
                .findBySpecializationAndVerificationStatus(requiredSpecialization, VerificationStatus.VERIFIED);

        // 3. Sort: same-location first, then by highest average rating
        return candidates.stream()
                .sorted(
                        Comparator
                                // Engineers in the same location come first (false < true, so we negate)
                                .comparing((Engineer e) -> !isLocationMatch(e.getLocation(), jobLocation))
                                // Then sort by highest average rating (descending)
                                .thenComparing(
                                        (Engineer e) -> e.getAverageRating() != null ? e.getAverageRating() : 0.0,
                                        Comparator.reverseOrder()
                                )
                )
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     * <p>
     * Sends a JOB_MATCHED notification to each matched engineer's user
     * and transitions the job status to MATCHED.
     */
    @Override
    @Transactional
    public void matchAndNotify(Long jobId) {
        // 1. Find matching engineers
        List<Engineer> matchedEngineers = findMatchingEngineers(jobId);

        // 2. Fetch the job again to update its status (transactional context)
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job", jobId));

        String specialization = job.getSpecializationRequired();

        // 3. Send notification to each matched engineer
        for (Engineer engineer : matchedEngineers) {
            notificationService.createNotification(
                    engineer.getUser().getId(),
                    "New Job Match",
                    "A new job matches your skills in " + specialization,
                    NotificationType.JOB_MATCHED
            );
        }

        // 4. Update job status to MATCHED
        job.setStatus(JobStatus.MATCHED);
        jobRepository.save(job);
    }

    /**
     * Checks if an engineer's location matches or is similar to the job location.
     * Uses case-insensitive containment for simple "nearby" matching.
     *
     * @param engineerLocation the engineer's location
     * @param jobLocation      the job's location
     * @return true if locations match or are similar
     */
    private boolean isLocationMatch(String engineerLocation, String jobLocation) {
        if (engineerLocation == null || jobLocation == null) {
            return false;
        }

        String engLoc = engineerLocation.trim().toLowerCase();
        String jobLoc = jobLocation.trim().toLowerCase();

        // Exact match or substring containment (simple "nearby" heuristic)
        return engLoc.equals(jobLoc)
                || engLoc.contains(jobLoc)
                || jobLoc.contains(engLoc);
    }
}
