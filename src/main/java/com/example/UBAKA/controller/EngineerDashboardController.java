package com.example.UBAKA.controller;

import com.example.UBAKA.model.Engineer;
import com.example.UBAKA.model.User;
import com.example.UBAKA.model.EngineerCertificate;
import com.example.UBAKA.model.JobApplication;
import com.example.UBAKA.model.Job;
import com.example.UBAKA.model.Notification;
import com.example.UBAKA.model.VerificationRequest;
import com.example.UBAKA.repository.EngineerRepository;
import com.example.UBAKA.repository.UserRepository;
import com.example.UBAKA.repository.VerificationRequestRepository;
import com.example.UBAKA.service.ApplicationService;
import com.example.UBAKA.service.JobService;
import com.example.UBAKA.service.NotificationService;
import com.example.UBAKA.model.enums.AvailabilityStatus;
import com.example.UBAKA.model.enums.VerificationStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/engineer")
public class EngineerDashboardController {

    private static final String UPLOAD_DIR = "uploads/";

    private final ApplicationService applicationService;
    private final NotificationService notificationService;
    private final JobService jobService;
    private final UserRepository userRepository;
    private final EngineerRepository engineerRepository;
    private final VerificationRequestRepository verificationRequestRepository;

    public EngineerDashboardController(ApplicationService applicationService,
                                       NotificationService notificationService,
                                       JobService jobService,
                                       UserRepository userRepository,
                                       EngineerRepository engineerRepository,
                                       VerificationRequestRepository verificationRequestRepository) {
        this.applicationService = applicationService;
        this.notificationService = notificationService;
        this.jobService = jobService;
        this.userRepository = userRepository;
        this.engineerRepository = engineerRepository;
        this.verificationRequestRepository = verificationRequestRepository;
    }

    @ModelAttribute
    public void addCommonAttributes(Authentication auth, Model model) {
        if (auth != null && auth.isAuthenticated()) {
            Engineer engineer = resolveEngineer(auth);
            model.addAttribute("engineer", engineer);
            model.addAttribute("engineerName", engineer.getUser().getFullName());
            model.addAttribute("profileStatus", engineer.getVerificationStatus().name());
            // add unread notifications count for sidebar badge
            try {
                Long userId = engineer.getUser().getId();
                List<Notification> notifs = notificationService.getUserNotifications(userId);
                long unread = notifs.stream().filter(n -> n.getIsRead() == null || !n.getIsRead()).count();
                model.addAttribute("unreadCount", unread);
            } catch (Exception ignored) {
                model.addAttribute("unreadCount", 0);
            }
        }
    }

    /** Resolves the logged-in user's Engineer record from the security context. */
    protected Engineer resolveEngineer(Authentication auth) {
        String email = auth.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found: " + email));
        Engineer engineer = user.getEngineer();
        if (engineer == null) {
            engineer = new Engineer();
            engineer.setUser(user);
            engineer.setVerificationStatus(VerificationStatus.PENDING);
            engineer.setAvailabilityStatus(AvailabilityStatus.AVAILABLE);
            engineer.setSpecialization("General");
            engineer = engineerRepository.save(engineer);
        }
        return engineer;
    }

    @GetMapping("/verification")
    public String engineerVerification() {
        return "Engineer/verification";
    }

    @PostMapping("/verification")
    public String engineerVerificationPost(@ModelAttribute Engineer engineerForm, 
                                           @RequestParam(value = "idFile", required = false) MultipartFile idFile,
                                           @RequestParam(value = "certificateFiles", required = false) MultipartFile[] certificateFiles,
                                           @RequestParam(value = "action", required = false) String action,
                                           Authentication auth) throws IOException {
        Engineer engineer = resolveEngineer(auth);
        engineer.setLocation(engineerForm.getLocation());
        engineer.setSpecialization(engineerForm.getSpecialization());
        engineer.setExperienceYears(engineerForm.getExperienceYears());
        engineer.setBio(engineerForm.getBio());
        engineer.setNationalIdNumber(engineerForm.getNationalIdNumber());
        engineer.setAvailabilityStatus(engineerForm.getAvailabilityStatus());

        if (idFile != null && !idFile.isEmpty()) {
            String fileName = UUID.randomUUID().toString() + "_" + idFile.getOriginalFilename();
            Path path = Paths.get(UPLOAD_DIR + fileName);
            Files.createDirectories(path.getParent());
            Files.write(path, idFile.getBytes());
            engineer.setIdDocumentPath("/uploads/" + fileName);
        }

        if (certificateFiles != null && certificateFiles.length > 0) {
            for (MultipartFile file : certificateFiles) {
                if (!file.isEmpty()) {
                    String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
                    Path path = Paths.get(UPLOAD_DIR + fileName);
                    Files.createDirectories(path.getParent());
                    Files.write(path, file.getBytes());

                    EngineerCertificate cert = new EngineerCertificate();
                    cert.setEngineer(engineer);
                    cert.setCertificatePath("/uploads/" + fileName);
                    cert.setCertificateName(file.getOriginalFilename());
                    engineer.getCertificates().add(cert);
                }
            }
        }

        if ("submit".equals(action)) {
            engineer.setVerificationStatus(VerificationStatus.PENDING);
            
            VerificationRequest request = new VerificationRequest();
            request.setEngineer(engineer);
            request.setSubmittedAt(LocalDateTime.now());
            request.setReviewStatus(VerificationStatus.PENDING);
            verificationRequestRepository.save(request);
        }

        engineerRepository.save(engineer);
        String redirectUrl = "redirect:/engineer/verification";
        return "submit".equals(action) ? redirectUrl + "?submitted" : redirectUrl + "?success";
    }

    @GetMapping("/dashboard")
    public String engineerDashboard(Authentication auth, Model model) {
        Engineer engineer = resolveEngineer(auth);
        // Calculate dynamic stats
        List<JobApplication> applications = applicationService.getApplicationsByEngineer(engineer.getId());
        model.addAttribute("activeApplications", applications.size());
        
        String rating = engineer.getAverageRating() != null ? engineer.getAverageRating() + "/5" : "No rating";
        model.addAttribute("rating", rating);
        
        List<Job> openJobs = jobService.getOpenJobs();
        model.addAttribute("openJobs", openJobs);
        return "Engineer/dashboard";
    }

    @GetMapping("/projects")
    public String engineerProjects(Authentication auth, Model model) {
        Engineer engineer = resolveEngineer(auth);
        List<JobApplication> applications = applicationService.getApplicationsByEngineer(engineer.getId());
        model.addAttribute("applications", applications);
        model.addAttribute("openJobs", jobService.getOpenJobs());
        return "Engineer/projects";
    }

    @GetMapping("/notifications")
    public String engineerNotifications(Authentication auth, Model model) {
        Engineer engineer = resolveEngineer(auth);
        // Notifications are linked to User, not Engineer
        Long userId = engineer.getUser().getId();
        List<Notification> notifications = notificationService.getUserNotifications(userId);
        model.addAttribute("notifications", notifications);
        return "Engineer/notifications";
    }

    @PostMapping("/notifications/{id}/read")
    public String markNotificationRead(@PathVariable("id") Long id, Authentication auth) {
        Engineer engineer = resolveEngineer(auth);
        Long userId = engineer.getUser().getId();
        Notification notification = notificationService.getNotificationById(id);
        if (!notification.getUser().getId().equals(userId)) {
            return "redirect:/engineer/notifications";
        }
        notificationService.markAsRead(id);
        return "redirect:/engineer/notifications";
    }

    @GetMapping("/settings")
    public String engineerSettings() {
        return "Engineer/settings";
    }

    @PostMapping("/applications")
    public String applyToJob(@RequestParam Long jobId, Authentication auth) {
        Engineer engineer = resolveEngineer(auth);
        if (engineer == null) return "redirect:/auth/login";

        applicationService.applyToJob(jobId, engineer.getId());
        return "redirect:/engineer/projects";
    }
}
