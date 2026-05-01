package com.example.UBAKA.repository;

import com.example.UBAKA.model.Job;
import com.example.UBAKA.model.enums.JobStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {

    List<Job> findByStatus(JobStatus status);

    List<Job> findByCustomerId(Long customerId);

    List<Job> findBySpecializationRequired(String specialization);
}