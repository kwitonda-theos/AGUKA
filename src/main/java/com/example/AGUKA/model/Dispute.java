package com.example.AGUKA.model;

import com.example.AGUKA.model.enums.DisputeStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "disputes")
public class Dispute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "job_id", nullable = false, unique = true)
    private Job job;

    @ManyToOne(optional = false)
    @JoinColumn(name = "opened_by", nullable = false)
    private User openedBy;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private DisputeStatus status;

    @ManyToOne
    @JoinColumn(name = "resolved_by")
    private User resolvedBy;

    @Column(name = "resolution_notes", columnDefinition = "TEXT")
    private String resolutionNotes;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "resolved_at")
    private LocalDateTime resolvedAt;

    public Dispute() {
    }

    public Dispute(Long id, Job job, User openedBy, String description, DisputeStatus status,
                   User resolvedBy, String resolutionNotes, LocalDateTime createdAt, LocalDateTime resolvedAt) {
        this.id = id;
        this.job = job;
        this.openedBy = openedBy;
        this.description = description;
        this.status = status;
        this.resolvedBy = resolvedBy;
        this.resolutionNotes = resolutionNotes;
        this.createdAt = createdAt;
        this.resolvedAt = resolvedAt;
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

    public User getOpenedBy() {
        return openedBy;
    }

    public String getDescription() {
        return description;
    }

    public DisputeStatus getStatus() {
        return status;
    }

    public User getResolvedBy() {
        return resolvedBy;
    }

    public String getResolutionNotes() {
        return resolutionNotes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getResolvedAt() {
        return resolvedAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public void setOpenedBy(User openedBy) {
        this.openedBy = openedBy;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(DisputeStatus status) {
        this.status = status;
    }

    public void setResolvedBy(User resolvedBy) {
        this.resolvedBy = resolvedBy;
    }

    public void setResolutionNotes(String resolutionNotes) {
        this.resolutionNotes = resolutionNotes;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setResolvedAt(LocalDateTime resolvedAt) {
        this.resolvedAt = resolvedAt;
    }
}