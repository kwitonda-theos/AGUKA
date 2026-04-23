package com.example.AGUKA.repository;

import com.example.AGUKA.model.VerificationRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VerificationRequestRepository extends JpaRepository<VerificationRequest, Long> {

    List<VerificationRequest> findByEngineerId(Long engineerId);
}