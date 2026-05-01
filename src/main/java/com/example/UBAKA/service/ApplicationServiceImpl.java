package com.example.UBAKA.service;

import com.example.UBAKA.exception.DuplicateApplicationException;
import com.example.UBAKA.exception.ResourceNotFoundException;
import com.example.UBAKA.model.Engineer;
import com.example.UBAKA.model.Job;
import com.example.UBAKA.model.JobApplication;
import com.example.UBAKA.model.enums.ApplicationStatus;
import com.example.UBAKA.model.enums.NotificationType;
import com.example.UBAKA.repository.EngineerRepository;
import com.example.UBAKA.repository.JobApplicationRepository;
import com.example.UBAKA.repository.JobRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementation of {@link ApplicationService} managing the lifecycle
 * of job applications: creation, acceptance, and rejection.
 */
@Service
public class ApplicationServiceImpl implements ApplicationService {

    private final JobRepository jobRepository;
    private final EngineerRepository engineerRepository;
    private final JobApplicationRepository jobApplicationRepository;
    private final NotificationService notificationService;

    public ApplicationServiceImpl(JobRepository jobRepository,
                                  EngineerRepository engineerRepository,
                                  JobApplicationRepository jobApplicationRepository,
                                  NotificationService notificationService) {
        this.jobRepository = jobRepository;
        this.engineerRepository = engineerRepository;
        this.jobApplicationRepository = jobApplicationRepository;
        this.notificationService = notificationService;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Validates that both the job and engineer exist, checks for duplicate
     * applications, then persists a new PENDING application and notifies
     * the job's customer.
     */
    @Override
    @Transactional
    public JobApplication applyToJob(Long jobId, Long engineerId) {
        // 1. Fetch job
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job", jobId));

        // 2. Fetch engineer
        Engineer engineer = engineerRepository.findById(engineerId)
                .orElseThrow(() -> new ResourceNotFoundException("Engineer", engineerId));

        // 3. Check for duplicate application
        jobApplicationRepository.findByJobIdAndEngineerId(jobId, engineerId)
                .ifPresent(existing -> {
                    throw new DuplicateApplicationException(jobId, engineerId);
                });

        // 4. Create and populate application
        JobApplication application = new JobApplication();
        application.setJob(job);
        application.setEngineer(engineer);
        application.setApplicationStatus(ApplicationStatus.PENDING);
        application.setCreatedAt(LocalDateTime.now());

        // 5. Save
        JobApplication savedApplication = jobApplicationRepository.save(application);

        // 6. Notify the customer who owns the job
        notificationService.createNotification(
                job.getCustomer().getUser(),
                "New Application",
                "An engineer has applied to your job: " + job.getTitle(),
                NotificationType.JOB_UPDATE
        );

        return savedApplication;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Sets the application status to ACCEPTED and notifies the engineer.
     */
    @Override
    @Transactional
    public JobApplication acceptApplication(Long applicationId) {
        // 1. Fetch application
        JobApplication application = jobApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("JobApplication", applicationId));

        // 2. Update status
        application.setApplicationStatus(ApplicationStatus.ACCEPTED);

        // 3. Save
        JobApplication updatedApplication = jobApplicationRepository.save(application);

        // 4. Notify engineer
        notificationService.createNotification(
                application.getEngineer().getUser(),
                "Application Accepted",
                "Your application for \"" + application.getJob().getTitle() + "\" has been accepted.",
                NotificationType.JOB_UPDATE
        );

        return updatedApplication;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Sets the application status to DECLINED and notifies the engineer.
     */
    @Override
    @Transactional
    public JobApplication declineApplication(Long applicationId) {
        // 1. Fetch application
        JobApplication application = jobApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("JobApplication", applicationId));

        // 2. Update status
        application.setApplicationStatus(ApplicationStatus.DECLINED);

        // 3. Save
        JobApplication updatedApplication = jobApplicationRepository.save(application);

        // 4. Notify engineer
        notificationService.createNotification(
                application.getEngineer().getUser(),
                "Application Declined",
                "Your application for \"" + application.getJob().getTitle() + "\" has been declined.",
                NotificationType.JOB_UPDATE
        );

        return updatedApplication;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<JobApplication> getApplicationsByJob(Long jobId) {
        return jobApplicationRepository.findByJobId(jobId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<JobApplication> getApplicationsByEngineer(Long engineerId) {
        return jobApplicationRepository.findByEngineerId(engineerId);
    }
}
