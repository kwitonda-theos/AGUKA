package com.example.AGUKA.model;

import com.example.AGUKA.model.enums.VerificationStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "verification_requests")
public class VerificationRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "engineer_id", nullable = false)
    private Engineer engineer;

    @Column(name = "submitted_at", nullable = false)
    private LocalDateTime submittedAt;

    @ManyToOne
    @JoinColumn(name = "reviewed_by")
    private User reviewedBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "review_status", nullable = false, length = 30)
    private VerificationStatus reviewStatus;

    @Column(columnDefinition = "TEXT")
    private String notes;

    public VerificationRequest() {
    }

    public VerificationRequest(Long id, Engineer engineer, LocalDateTime submittedAt, User reviewedBy,
                               VerificationStatus reviewStatus, String notes) {
        this.id = id;
        this.engineer = engineer;
        this.submittedAt = submittedAt;
        this.reviewedBy = reviewedBy;
        this.reviewStatus = reviewStatus;
        this.notes = notes;
    }

    @PrePersist
    public void prePersist() {
        this.submittedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Engineer getEngineer() {
        return engineer;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public User getReviewedBy() {
        return reviewedBy;
    }

    public VerificationStatus getReviewStatus() {
        return reviewStatus;
    }

    public String getNotes() {
        return notes;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEngineer(Engineer engineer) {
        this.engineer = engineer;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }

    public void setReviewedBy(User reviewedBy) {
        this.reviewedBy = reviewedBy;
    }

    public void setReviewStatus(VerificationStatus reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}