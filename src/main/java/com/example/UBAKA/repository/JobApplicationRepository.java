package com.example.UBAKA.repository;

import com.example.UBAKA.model.JobApplication;
import com.example.UBAKA.model.enums.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

    List<JobApplication> findByJobId(Long jobId);

    List<JobApplication> findByEngineerId(Long engineerId);

    List<JobApplication> findByApplicationStatus(ApplicationStatus status);

    Optional<JobApplication> findByJobIdAndEngineerId(Long jobId, Long engineerId);
}