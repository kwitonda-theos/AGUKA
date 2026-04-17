package com.example.AGUKA.model;

import com.example.AGUKA.model.enums.ApplicationStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "job_applications")
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    @ManyToOne(optional = false)
    @JoinColumn(name = "engineer_id", nullable = false)
    private Engineer engineer;

    @Enumerated(EnumType.STRING)
    @Column(name = "application_status", nullable = false, length = 30)
    private ApplicationStatus applicationStatus;

    @Column(name = "matched_score")
    private Double matchedScore;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public JobApplication() {
    }

    public JobApplication(Long id, Job job, Engineer engineer, ApplicationStatus applicationStatus,
                          Double matchedScore, LocalDateTime createdAt) {
        this.id = id;
        this.job = job;
        this.engineer = engineer;
        this.applicationStatus = applicationStatus;
        this.matchedScore = matchedScore;
        this.createdAt = createdAt;
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
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

    public ApplicationStatus getApplicationStatus() {
        return applicationStatus;
    }

    public Double getMatchedScore() {
        return matchedScore;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
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

    public void setApplicationStatus(ApplicationStatus applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public void setMatchedScore(Double matchedScore) {
        this.matchedScore = matchedScore;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}