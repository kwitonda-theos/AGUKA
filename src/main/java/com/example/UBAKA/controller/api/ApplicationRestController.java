package com.example.UBAKA.controller.api;

import com.example.UBAKA.model.JobApplication;
import com.example.UBAKA.service.ApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller exposing Job Application endpoints for Postman testing.
 */
@RestController
@RequestMapping("/api/applications")
public class ApplicationRestController {

    private final ApplicationService applicationService;

    public ApplicationRestController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    /**
     * POST /api/applications?jobId={id}&engineerId={id}
     * Engineer applies to a job.
     */
    @PostMapping
    public ResponseEntity<JobApplication> applyToJob(@RequestParam Long jobId,
                                                      @RequestParam Long engineerId) {
        JobApplication application = applicationService.applyToJob(jobId, engineerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(application);
    }

    /**
     * PATCH /api/applications/{applicationId}/accept
     * Customer accepts an application.
     */
    @PatchMapping("/{applicationId}/accept")
    public ResponseEntity<JobApplication> acceptApplication(@PathVariable Long applicationId) {
        JobApplication accepted = applicationService.acceptApplication(applicationId);
        return ResponseEntity.ok(accepted);
    }

    /**
     * PATCH /api/applications/{applicationId}/decline
     * Customer declines an application.
     */
    @PatchMapping("/{applicationId}/decline")
    public ResponseEntity<JobApplication> declineApplication(@PathVariable Long applicationId) {
        JobApplication declined = applicationService.declineApplication(applicationId);
        return ResponseEntity.ok(declined);
    }

    /**
     * GET /api/applications/job/{jobId}
     * Get all applications for a specific job.
     */
    @GetMapping("/job/{jobId}")
    public ResponseEntity<List<JobApplication>> getApplicationsByJob(@PathVariable Long jobId) {
        List<JobApplication> applications = applicationService.getApplicationsByJob(jobId);
        return ResponseEntity.ok(applications);
    }

    /**
     * GET /api/applications/engineer/{engineerId}
     * Get all applications submitted by a specific engineer.
     */
    @GetMapping("/engineer/{engineerId}")
    public ResponseEntity<List<JobApplication>> getApplicationsByEngineer(@PathVariable Long engineerId) {
        List<JobApplication> applications = applicationService.getApplicationsByEngineer(engineerId);
        return ResponseEntity.ok(applications);
    }
}
