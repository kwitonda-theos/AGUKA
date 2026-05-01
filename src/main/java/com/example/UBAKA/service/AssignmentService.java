package com.example.UBAKA.service;

import com.example.UBAKA.model.Assignment;
import java.util.Optional;

public interface AssignmentService {
    Assignment assignEngineer(Long jobId, Long engineerId);
    Assignment startJob(Long assignmentId);
    Assignment completeJob(Long assignmentId);
    Optional<Assignment> getAssignmentByJob(Long jobId);
}
