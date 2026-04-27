package com.example.AGUKA.service;

import com.example.AGUKA.model.Job;
import com.example.AGUKA.model.enums.JobStatus;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for Job-related business operations.
 */
public interface JobService {

    /**
     * Creates a new job and assigns it to the specified customer.
     *
     * @param job        the job to create
     * @param customerId the ID of the customer who owns this job
     * @return the persisted job
     */
    Job createJob(Job job, Long customerId);

    /**
     * Retrieves all jobs belonging to a specific customer.
     *
     * @param customerId the customer's ID
     * @return list of jobs for the customer
     */
    List<Job> getJobsByCustomer(Long customerId);

    /**
     * Retrieves all jobs with status OPEN.
     *
     * @return list of open jobs
     */
    List<Job> getOpenJobs();

    /**
     * Updates the status of an existing job.
     *
     * @param jobId  the ID of the job to update
     * @param status the new status
     * @return the updated job
     */
    Job updateJobStatus(Long jobId, JobStatus status);

    /**
     * Retrieves a job by its ID.
     *
     * @param jobId the job's ID
     * @return an Optional containing the job if found
     */
    Optional<Job> getJobById(Long jobId);
}
