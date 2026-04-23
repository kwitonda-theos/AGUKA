package com.example.AGUKA.repository;

import com.example.AGUKA.model.Job;
import com.example.AGUKA.model.enums.JobStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {

    List<Job> findByStatus(JobStatus status);

    List<Job> findByCustomerId(Long customerId);

    List<Job> findBySpecializationRequired(String specialization);
}