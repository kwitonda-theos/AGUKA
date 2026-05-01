package com.example.UBAKA.repository;

import com.example.UBAKA.model.Engineer;
import com.example.UBAKA.model.enums.AvailabilityStatus;
import com.example.UBAKA.model.enums.VerificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EngineerRepository extends JpaRepository<Engineer, Long> {

    List<Engineer> findBySpecialization(String specialization);

    List<Engineer> findByAvailabilityStatus(AvailabilityStatus status);

    List<Engineer> findBySpecializationAndVerificationStatus(String specialization, VerificationStatus status);
}