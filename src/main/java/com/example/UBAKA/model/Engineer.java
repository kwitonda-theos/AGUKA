package com.example.UBAKA.model;

import com.example.UBAKA.model.enums.AvailabilityStatus;
import com.example.UBAKA.model.enums.VerificationStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "engineers")
public class Engineer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    @JsonIgnoreProperties({"engineer", "customer"})
    private User user;

    @Column(nullable = false, length = 100)
    private String specialization;

    @Column(name = "experience_years")
    private Integer experienceYears;

    @Column(length = 150)
    private String location;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @Column(name = "national_id_number", length = 50)
    private String nationalIdNumber;

    @Column(name = "id_document_path")
    private String idDocumentPath;

    @Enumerated(EnumType.STRING)
    @Column(name = "verification_status", nullable = false, length = 30)
    private VerificationStatus verificationStatus;

    @Column(name = "average_rating")
    private Double averageRating;

    @Column(name = "total_reviews")
    private Integer totalReviews;

    @Enumerated(EnumType.STRING)
    @Column(name = "availability_status", nullable = false, length = 30)
    private AvailabilityStatus availabilityStatus;

    @OneToMany(mappedBy = "engineer", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"engineer"})
    private List<JobApplication> jobApplications = new ArrayList<>();

    @OneToMany(mappedBy = "engineer", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"engineer"})
    private List<Assignment> assignments = new ArrayList<>();

    @OneToMany(mappedBy = "engineer", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"engineer"})
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "engineer", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"engineer"})
    private List<VerificationRequest> verificationRequests = new ArrayList<>();

    @OneToMany(mappedBy = "engineer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"engineer"})
    private List<EngineerCertificate> certificates = new ArrayList<>();

    public Engineer() {
    }

    public Engineer(Long id, User user, String specialization, Integer experienceYears, String location, String bio,
                    String nationalIdNumber, String idDocumentPath, VerificationStatus verificationStatus,
                    Double averageRating, Integer totalReviews, AvailabilityStatus availabilityStatus,
                    List<JobApplication> jobApplications, List<Assignment> assignments,
                    List<Review> reviews, List<VerificationRequest> verificationRequests) {
        this.id = id;
        this.user = user;
        this.specialization = specialization;
        this.experienceYears = experienceYears;
        this.location = location;
        this.bio = bio;
        this.nationalIdNumber = nationalIdNumber;
        this.idDocumentPath = idDocumentPath;
        this.verificationStatus = verificationStatus;
        this.averageRating = averageRating;
        this.totalReviews = totalReviews;
        this.availabilityStatus = availabilityStatus;
        this.jobApplications = jobApplications;
        this.assignments = assignments;
        this.reviews = reviews;
        this.verificationRequests = verificationRequests;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getSpecialization() {
        return specialization;
    }

    public Integer getExperienceYears() {
        return experienceYears;
    }

    public String getLocation() {
        return location;
    }

    public String getBio() {
        return bio;
    }

    public String getNationalIdNumber() {
        return nationalIdNumber;
    }

    public String getIdDocumentPath() {
        return idDocumentPath;
    }

    public VerificationStatus getVerificationStatus() {
        return verificationStatus;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public Integer getTotalReviews() {
        return totalReviews;
    }

    public AvailabilityStatus getAvailabilityStatus() {
        return availabilityStatus;
    }

    public List<JobApplication> getJobApplications() {
        return jobApplications;
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public List<VerificationRequest> getVerificationRequests() {
        return verificationRequests;
    }

    public List<EngineerCertificate> getCertificates() {
        return certificates;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public void setExperienceYears(Integer experienceYears) {
        this.experienceYears = experienceYears;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setNationalIdNumber(String nationalIdNumber) {
        this.nationalIdNumber = nationalIdNumber;
    }

    public void setIdDocumentPath(String idDocumentPath) {
        this.idDocumentPath = idDocumentPath;
    }

    public void setVerificationStatus(VerificationStatus verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public void setTotalReviews(Integer totalReviews) {
        this.totalReviews = totalReviews;
    }

    public void setAvailabilityStatus(AvailabilityStatus availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    public void setJobApplications(List<JobApplication> jobApplications) {
        this.jobApplications = jobApplications;
    }

    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public void setVerificationRequests(List<VerificationRequest> verificationRequests) {
        this.verificationRequests = verificationRequests;
    }

    public void setCertificates(List<EngineerCertificate> certificates) {
        this.certificates = certificates;
    }
}