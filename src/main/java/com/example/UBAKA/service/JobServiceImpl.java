package com.example.UBAKA.service;

import com.example.UBAKA.exception.ResourceNotFoundException;
import com.example.UBAKA.model.Customer;
import com.example.UBAKA.model.Job;
import com.example.UBAKA.model.enums.JobStatus;
import com.example.UBAKA.repository.CustomerRepository;
import com.example.UBAKA.repository.JobRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of {@link JobService} providing business logic
 * for creating, retrieving, and updating jobs.
 */
@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final CustomerRepository customerRepository;

    /**
     * Constructor injection for required dependencies.
     */
    public JobServiceImpl(JobRepository jobRepository, CustomerRepository customerRepository) {
        this.jobRepository = jobRepository;
        this.customerRepository = customerRepository;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Fetches the customer by ID, assigns it to the job, sets the initial
     * status to OPEN and the creation timestamp, then persists the job.
     */
    @Override
    @Transactional
    public Job createJob(Job job, Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", customerId));

        job.setCustomer(customer);
        job.setStatus(JobStatus.OPEN);
        job.setCreatedAt(LocalDateTime.now());

        return jobRepository.save(job);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Job> getJobsByCustomer(Long customerId) {
        // Verify the customer exists before querying jobs
        if (!customerRepository.existsById(customerId)) {
            throw new ResourceNotFoundException("Customer", customerId);
        }
        return jobRepository.findByCustomerId(customerId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Job> getOpenJobs() {
        return jobRepository.findByStatus(JobStatus.OPEN);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Finds the job by ID, updates its status, and persists the change.
     * Throws {@link ResourceNotFoundException} if the job does not exist.
     */
    @Override
    @Transactional
    public Job updateJobStatus(Long jobId, JobStatus status) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job", jobId));

        job.setStatus(status);

        return jobRepository.save(job);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Job> getJobById(Long jobId) {
        return jobRepository.findById(jobId);
    }
}
