package com.example.AGUKA.model;

import com.example.AGUKA.model.enums.AssignmentStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "assignments")
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "job_id", nullable = false, unique = true)
    private Job job;

    @ManyToOne(optional = false)
    @JoinColumn(name = "engineer_id", nullable = false)
    private Engineer engineer;

    @Column(name = "assigned_at", nullable = false)
    private LocalDateTime assignedAt;

    @Column(name = "started_at")
    private LocalDateTime startedAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private AssignmentStatus status;

    public Assignment() {
    }

    public Assignment(Long id, Job job, Engineer engineer, LocalDateTime assignedAt,
                      LocalDateTime startedAt, LocalDateTime completedAt, AssignmentStatus status) {
        this.id = id;
        this.job = job;
        this.engineer = engineer;
        this.assignedAt = assignedAt;
        this.startedAt = startedAt;
        this.completedAt = completedAt;
        this.status = status;
    }

    @PrePersist
    public void prePersist() {
        this.assignedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Job getJob() {
        return job;
    }

    public Engineer getEngineer() {
        return engineer;
    }

    public LocalDateTime getAssignedAt() {
        return assignedAt;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public AssignmentStatus getStatus() {
        return status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public void setEngineer(Engineer engineer) {
        this.engineer = engineer;
    }

    public void setAssignedAt(LocalDateTime assignedAt) {
        this.assignedAt = assignedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public void setStatus(AssignmentStatus status) {
        this.status = status;
    }
}