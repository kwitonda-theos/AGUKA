package com.example.AGUKA.repository;

import com.example.AGUKA.model.Engineer;
import com.example.AGUKA.model.enums.AvailabilityStatus;
import com.example.AGUKA.model.enums.VerificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EngineerRepository extends JpaRepository<Engineer, Long> {

    List<Engineer> findBySpecialization(String specialization);

    List<Engineer> findByAvailabilityStatus(AvailabilityStatus status);

    List<Engineer> findBySpecializationAndVerificationStatus(String specialization, VerificationStatus status);
}