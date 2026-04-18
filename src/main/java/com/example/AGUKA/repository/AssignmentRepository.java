package com.example.AGUKA.repository;

import com.example.AGUKA.model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    Optional<Assignment> findByJobId(Long jobId);
}