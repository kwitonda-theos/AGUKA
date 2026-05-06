package com.example.UBAKA.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "engineer_certificates")
public class EngineerCertificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "engineer_id", nullable = false)
    @JsonIgnoreProperties({"certificates"})
    private Engineer engineer;

    @Column(name = "certificate_path", nullable = false)
    private String certificatePath;

    @Column(name = "certificate_name")
    private String certificateName;

    public EngineerCertificate() {
    }

    public EngineerCertificate(Engineer engineer, String certificatePath, String certificateName) {
        this.engineer = engineer;
        this.certificatePath = certificatePath;
        this.certificateName = certificateName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Engineer getEngineer() {
        return engineer;
    }

    public void setEngineer(Engineer engineer) {
        this.engineer = engineer;
    }

    public String getCertificatePath() {
        return certificatePath;
    }

    public void setCertificatePath(String certificatePath) {
        this.certificatePath = certificatePath;
    }

    public String getCertificateName() {
        return certificateName;
    }

    public void setCertificateName(String certificateName) {
        this.certificateName = certificateName;
    }
}
