package com.example.AGUKA.service;

import com.example.AGUKA.model.Engineer;

import java.util.List;

/**
 * Service interface for matching engineers to jobs based on
 * specialization, location, verification status, and rating.
 */
public interface MatchingService {

    /**
     * Finds engineers that match a given job's requirements.
     * <p>
     * Matching criteria:
     * <ul>
     *   <li>Engineer specialization matches job's required specialization</li>
     *   <li>Engineer verification status is APPROVED</li>
     *   <li>Engineers in the same location are prioritized</li>
     *   <li>Results sorted by highest average rating first</li>
     * </ul>
     *
     * @param jobId the ID of the job to match engineers for
     * @return list of matching engineers, sorted by relevance
     */
    List<Engineer> findMatchingEngineers(Long jobId);

    /**
     * Finds matching engineers for a job, sends them notifications,
     * and updates the job status to MATCHED.
     *
     * @param jobId the ID of the job to match and notify for
     */
    void matchAndNotify(Long jobId);
}
