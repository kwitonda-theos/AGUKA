package com.example.UBAKA.controller.api;

import com.example.UBAKA.model.Engineer;
import com.example.UBAKA.service.MatchingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller exposing Matching endpoints for Postman testing.
 */
@RestController
@RequestMapping("/api/matching")
public class MatchingRestController {

    private final MatchingService matchingService;

    public MatchingRestController(MatchingService matchingService) {
        this.matchingService = matchingService;
    }

    /**
     * GET /api/matching/job/{jobId}
     * Find all engineers that match a given job's requirements.
     * Returns the list sorted by location relevance and rating.
     */
    @GetMapping("/job/{jobId}")
    public ResponseEntity<List<Engineer>> findMatchingEngineers(@PathVariable Long jobId) {
        List<Engineer> engineers = matchingService.findMatchingEngineers(jobId);
        return ResponseEntity.ok(engineers);
    }

    /**
     * POST /api/matching/job/{jobId}/notify
     * Find matching engineers, send them notifications,
     * and update the job status to MATCHED.
     */
    @PostMapping("/job/{jobId}/notify")
    public ResponseEntity<String> matchAndNotify(@PathVariable Long jobId) {
        matchingService.matchAndNotify(jobId);
        return ResponseEntity.ok("Matching completed and notifications sent for Job ID: " + jobId);
    }
}
