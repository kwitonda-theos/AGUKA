package com.example.UBAKA.controller.api;

import com.example.UBAKA.model.Job;
import com.example.UBAKA.model.enums.JobStatus;
import com.example.UBAKA.service.JobService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller exposing Job endpoints for Postman testing.
 */
@RestController
@RequestMapping("/api/jobs")
public class JobRestController {

    private final JobService jobService;

    public JobRestController(JobService jobService) {
        this.jobService = jobService;
    }

    /**
     * POST /api/jobs?customerId={id}
     * Create a new job for the given customer.
     *
     * Example body:
     * {
     *   "title": "Fix plumbing",
     *   "description": "Kitchen sink is leaking",
     *   "specializationRequired": "Plumbing",
     *   "location": "Kigali",
     *   "budget": 50000,
     *   "preferredDate": "2026-05-10"
     * }
     */
    @PostMapping
    public ResponseEntity<Job> createJob(@RequestBody Job job,
                                         @RequestParam Long customerId) {
        Job created = jobService.createJob(job, customerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * GET /api/jobs/customer/{customerId}
     * Retrieve all jobs belonging to a customer.
     */
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Job>> getJobsByCustomer(@PathVariable Long customerId) {
        List<Job> jobs = jobService.getJobsByCustomer(customerId);
        return ResponseEntity.ok(jobs);
    }

    /**
     * GET /api/jobs/open
     * Retrieve all jobs with status OPEN.
     */
    @GetMapping("/open")
    public ResponseEntity<List<Job>> getOpenJobs() {
        List<Job> jobs = jobService.getOpenJobs();
        return ResponseEntity.ok(jobs);
    }

    /**
     * GET /api/jobs/{jobId}
     * Retrieve a single job by its ID.
     */
    @GetMapping("/{jobId}")
    public ResponseEntity<Job> getJobById(@PathVariable Long jobId) {
        return jobService.getJobById(jobId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * PATCH /api/jobs/{jobId}/status?status={STATUS}
     * Update the status of an existing job.
     * Valid statuses: OPEN, MATCHED, IN_PROGRESS, COMPLETED, CANCELLED
     */
    @PatchMapping("/{jobId}/status")
    public ResponseEntity<Job> updateJobStatus(@PathVariable Long jobId,
                                                @RequestParam JobStatus status) {
        Job updated = jobService.updateJobStatus(jobId, status);
        return ResponseEntity.ok(updated);
    }
}
