package com.example.AGUKA.repository;

import com.example.AGUKA.model.JobApplication;
import com.example.AGUKA.model.enums.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

    List<JobApplication> findByJobId(Long jobId);

    List<JobApplication> findByEngineerId(Long engineerId);

    List<JobApplication> findByApplicationStatus(ApplicationStatus status);
}