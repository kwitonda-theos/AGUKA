package com.example.UBAKA.repository;

import com.example.UBAKA.model.Dispute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DisputeRepository extends JpaRepository<Dispute, Long> {

    Optional<Dispute> findByJobId(Long jobId);
}