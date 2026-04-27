package com.example.AGUKA.service;

import com.example.AGUKA.model.JobApplication;

import java.util.List;

/**
 * Service interface for managing job applications — how engineers
 * apply to jobs and how customers accept or decline them.
 */
public interface ApplicationService {

    /**
     * Creates a new application for the given engineer on the given job.
     * Prevents duplicate applications.
     *
     * @param jobId      the job to apply to
     * @param engineerId the engineer applying
     * @return the persisted application with status PENDING
     */
    JobApplication applyToJob(Long jobId, Long engineerId);

    /**
     * Accepts an existing application and notifies the engineer.
     *
     * @param applicationId the application to accept
     * @return the updated application with status ACCEPTED
     */
    JobApplication acceptApplication(Long applicationId);

    /**
     * Declines an existing application and notifies the engineer.
     *
     * @param applicationId the application to decline
     * @return the updated application with status DECLINED
     */
    JobApplication declineApplication(Long applicationId);

    /**
     * Retrieves all applications for a specific job.
     *
     * @param jobId the job's ID
     * @return list of applications
     */
    List<JobApplication> getApplicationsByJob(Long jobId);

    /**
     * Retrieves all applications submitted by a specific engineer.
     *
     * @param engineerId the engineer's ID
     * @return list of applications
     */
    List<JobApplication> getApplicationsByEngineer(Long engineerId);
}
