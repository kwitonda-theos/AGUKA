package com.example.UBAKA.controller;

import com.example.UBAKA.model.Engineer;
import com.example.UBAKA.model.Job;
import com.example.UBAKA.model.JobApplication;
import com.example.UBAKA.model.Notification;
import com.example.UBAKA.model.User;
import com.example.UBAKA.repository.EngineerRepository;
import com.example.UBAKA.repository.UserRepository;
import com.example.UBAKA.service.ApplicationService;
import com.example.UBAKA.service.JobService;
import com.example.UBAKA.service.NotificationService;
import com.example.UBAKA.model.enums.AvailabilityStatus;
import com.example.UBAKA.model.enums.VerificationStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/engineer")
public class EngineerDashboardController {

    private final ApplicationService applicationService;
    private final NotificationService notificationService;
    private final JobService jobService;
    private final UserRepository userRepository;
    private final EngineerRepository engineerRepository;

    public EngineerDashboardController(ApplicationService applicationService,
                                       NotificationService notificationService,
                                       JobService jobService,
                                       UserRepository userRepository,
                                       EngineerRepository engineerRepository) {
        this.applicationService = applicationService;
        this.notificationService = notificationService;
        this.jobService = jobService;
        this.userRepository = userRepository;
        this.engineerRepository = engineerRepository;
    }

    @ModelAttribute
    public void addCommonAttributes(Authentication auth, Model model) {
        if (auth != null && auth.isAuthenticated()) {
            Engineer engineer = getEngineer(auth);
            model.addAttribute("engineer", engineer);
            model.addAttribute("engineerName", engineer.getUser().getFullName());
            model.addAttribute("profileStatus", engineer.getVerificationStatus().name());
        }
    }

    /** Resolves the logged-in user's Engineer record from the security context. */
    private Engineer getEngineer(Authentication auth) {
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
    public String engineerVerificationPost(@ModelAttribute Engineer engineerForm, Authentication auth) {
        Engineer engineer = getEngineer(auth);
        engineer.setLocation(engineerForm.getLocation());
        engineer.setSpecialization(engineerForm.getSpecialization());
        engineer.setExperienceYears(engineerForm.getExperienceYears());
        engineer.setBio(engineerForm.getBio());
        engineer.setNationalIdNumber(engineerForm.getNationalIdNumber());
        engineer.setAvailabilityStatus(engineerForm.getAvailabilityStatus());
        engineerRepository.save(engineer);
        return "redirect:/engineer/verification?success";
    }

    @GetMapping("/dashboard")
    public String engineerDashboard(Authentication auth, Model model) {
        Engineer engineer = getEngineer(auth);
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
        Engineer engineer = getEngineer(auth);
        List<JobApplication> applications = applicationService.getApplicationsByEngineer(engineer.getId());
        model.addAttribute("applications", applications);
        model.addAttribute("openJobs", jobService.getOpenJobs());
        return "Engineer/projects";
    }

    @GetMapping("/notifications")
    public String engineerNotifications(Authentication auth, Model model) {
        Engineer engineer = getEngineer(auth);
        // Notifications are linked to User, not Engineer
        Long userId = engineer.getUser().getId();
        List<Notification> notifications = notificationService.getUserNotifications(userId);
        model.addAttribute("notifications", notifications);
        return "Engineer/notifications";
    }

    @GetMapping("/settings")
    public String engineerSettings() {
        return "Engineer/settings";
    }

    @PostMapping("/applications")
    public String applyToJob(@RequestParam Long jobId, Authentication auth) {
        Engineer engineer = getEngineer(auth);
        if (engineer == null) return "redirect:/auth/login";

        applicationService.applyToJob(jobId, engineer.getId());
        return "redirect:/engineer/projects";
    }
}
