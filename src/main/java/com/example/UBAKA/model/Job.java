package com.example.UBAKA.model;

import com.example.UBAKA.model.enums.JobStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "jobs")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonIgnoreProperties({"jobs", "reviews", "user"})
    private Customer customer;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "specialization_required", nullable = false, length = 100)
    private String specializationRequired;

    @Column(length = 150)
    private String location;

    @Column(precision = 12, scale = 2)
    private BigDecimal budget;

    @Column(name = "preferred_date")
    private LocalDate preferredDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private JobStatus status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"job"})
    private List<JobApplication> jobApplications = new ArrayList<>();

    @OneToOne(mappedBy = "job", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"job"})
    private Assignment assignment;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"job"})
    private List<Message> messages = new ArrayList<>();

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"job"})
    private List<Review> reviews = new ArrayList<>();

    @OneToOne(mappedBy = "job", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"job"})
    private Dispute dispute;

    public Job() {
    }

    public Job(Long id, Customer customer, String title, String description, String specializationRequired,
               String location, BigDecimal budget, LocalDate preferredDate, JobStatus status,
               LocalDateTime createdAt, LocalDateTime updatedAt, List<JobApplication> jobApplications,
               Assignment assignment, List<Message> messages, List<Review> reviews, Dispute dispute) {
        this.id = id;
        this.customer = customer;
        this.title = title;
        this.description = description;
        this.specializationRequired = specializationRequired;
        this.location = location;
        this.budget = budget;
        this.preferredDate = preferredDate;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.jobApplications = jobApplications;
        this.assignment = assignment;
        this.messages = messages;
        this.reviews = reviews;
        this.dispute = dispute;
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getSpecializationRequired() {
        return specializationRequired;
    }

    public String getLocation() {
        return location;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public LocalDate getPreferredDate() {
        return preferredDate;
    }

    public JobStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public List<JobApplication> getJobApplications() {
        return jobApplications;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public Dispute getDispute() {
        return dispute;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSpecializationRequired(String specializationRequired) {
        this.specializationRequired = specializationRequired;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public void setPreferredDate(LocalDate preferredDate) {
        this.preferredDate = preferredDate;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setJobApplications(List<JobApplication> jobApplications) {
        this.jobApplications = jobApplications;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public void setDispute(Dispute dispute) {
        this.dispute = dispute;
    }
}