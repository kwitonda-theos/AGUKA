package com.example.UBAKA.service;

import com.example.UBAKA.model.Assignment;
import com.example.UBAKA.model.Engineer;
import com.example.UBAKA.model.Job;
import com.example.UBAKA.model.JobApplication;
import com.example.UBAKA.model.enums.ApplicationStatus;
import com.example.UBAKA.model.enums.AssignmentStatus;
import com.example.UBAKA.model.enums.JobStatus;
import com.example.UBAKA.model.enums.NotificationType;
import com.example.UBAKA.repository.AssignmentRepository;
import com.example.UBAKA.repository.EngineerRepository;
import com.example.UBAKA.repository.JobApplicationRepository;
import com.example.UBAKA.repository.JobRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AssignmentServiceImpl implements AssignmentService {

    private final JobRepository jobRepository;
    private final EngineerRepository engineerRepository;
    private final AssignmentRepository assignmentRepository;
    private final JobApplicationRepository jobApplicationRepository;
    private final NotificationService notificationService;

    public AssignmentServiceImpl(JobRepository jobRepository,
                                 EngineerRepository engineerRepository,
                                 AssignmentRepository assignmentRepository,
                                 JobApplicationRepository jobApplicationRepository,
                                 NotificationService notificationService) {
        this.jobRepository = jobRepository;
        this.engineerRepository = engineerRepository;
        this.assignmentRepository = assignmentRepository;
        this.jobApplicationRepository = jobApplicationRepository;
        this.notificationService = notificationService;
    }

    @Override
    @Transactional
    public Assignment assignEngineer(Long jobId, Long engineerId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        Engineer engineer = engineerRepository.findById(engineerId)
                .orElseThrow(() -> new RuntimeException("Engineer not found"));

        if (job.getStatus() != JobStatus.OPEN && job.getStatus() != JobStatus.MATCHED) {
            throw new RuntimeException("Job is not in a valid state to be assigned");
        }

        if (assignmentRepository.findByJobId(jobId).isPresent()) {
            throw new RuntimeException("Assignment already exists for this job");
        }

        List<JobApplication> applications = jobApplicationRepository.findByJobId(jobId);
        
        boolean hasApplied = applications.stream()
                .anyMatch(app -> app.getEngineer().getId().equals(engineerId));
                
        // Validation: "Engineer must have applied OR be matched"
        // Since we don't have a distinct "match" entity other than application/job status, 
        // we'll assume the engineer is valid if they applied, or if job is in MATCHED state.
        if (!hasApplied && job.getStatus() != JobStatus.MATCHED) {
            throw new RuntimeException("Engineer has not applied and job is not matched");
        }

        // Update applications
        for (JobApplication app : applications) {
            if (app.getEngineer().getId().equals(engineerId)) {
                app.setApplicationStatus(ApplicationStatus.ACCEPTED);
            } else {
                app.setApplicationStatus(ApplicationStatus.DECLINED);
            }
            jobApplicationRepository.save(app);
        }

        Assignment assignment = new Assignment();
        assignment.setJob(job);
        assignment.setEngineer(engineer);
        assignment.setStatus(AssignmentStatus.ASSIGNED);
        assignment.setAssignedAt(LocalDateTime.now());

        job.setStatus(JobStatus.ASSIGNED);
        jobRepository.save(job);

        Assignment savedAssignment = assignmentRepository.save(assignment);

        // Notify Engineer
        notificationService.createNotification(
                engineer.getUser().getId(),
                "Job Assigned",
                "You have been assigned to a job: " + job.getTitle(),
                NotificationType.ENGINEER_SELECTED
        );

        // Notify Customer
        notificationService.createNotification(
                job.getCustomer().getUser().getId(),
                "Engineer Assigned",
                "Engineer selected successfully for job: " + job.getTitle(),
                NotificationType.ENGINEER_SELECTED
        );

        return savedAssignment;
    }

    @Override
    @Transactional
    public Assignment startJob(Long assignmentId) {
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new RuntimeException("Assignment not found"));

        assignment.setStatus(AssignmentStatus.IN_PROGRESS);
        assignment.setStartedAt(LocalDateTime.now());

        Job job = assignment.getJob();
        job.setStatus(JobStatus.IN_PROGRESS);
        jobRepository.save(job);

        Assignment updatedAssignment = assignmentRepository.save(assignment);

        notificationService.createNotification(
                job.getCustomer().getUser().getId(),
                "Job Started",
                "Job has started: " + job.getTitle(),
                NotificationType.JOB_STARTED
        );

        return updatedAssignment;
    }

    @Override
    @Transactional
    public Assignment completeJob(Long assignmentId) {
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new RuntimeException("Assignment not found"));

        assignment.setStatus(AssignmentStatus.COMPLETED);
        assignment.setCompletedAt(LocalDateTime.now());

        Job job = assignment.getJob();
        job.setStatus(JobStatus.COMPLETED);
        jobRepository.save(job);

        Assignment updatedAssignment = assignmentRepository.save(assignment);

        notificationService.createNotification(
                job.getCustomer().getUser().getId(),
                "Job Completed",
                "Job completed. Please leave a review for: " + job.getTitle(),
                NotificationType.JOB_COMPLETED
        );

        return updatedAssignment;
    }

    @Override
    public Optional<Assignment> getAssignmentByJob(Long jobId) {
        return assignmentRepository.findByJobId(jobId);
    }
}
