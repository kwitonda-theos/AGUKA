package com.example.UBAKA.controller;

import com.example.UBAKA.model.Job;
import com.example.UBAKA.model.JobApplication;
import com.example.UBAKA.model.Notification;
import com.example.UBAKA.service.ApplicationService;
import com.example.UBAKA.service.JobService;
import com.example.UBAKA.service.NotificationService;
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

    // Hardcoded for now, would typically come from Spring Security Context
    private final Long CURRENT_ENGINEER_ID = 2L;

    public EngineerDashboardController(ApplicationService applicationService,
                                       NotificationService notificationService,
                                       JobService jobService) {
        this.applicationService = applicationService;
        this.notificationService = notificationService;
        this.jobService = jobService;
    }

    @GetMapping("/verification")
    public String engineerVerification() {
        return "Engineer/verification";
    }

    @GetMapping("/dashboard")
    public String engineerDashboard(Model model) {
        List<Job> openJobs = jobService.getOpenJobs();
        model.addAttribute("openJobs", openJobs);
        return "Engineer/dashboard";
    }

    @GetMapping("/projects")
    public String engineerProjects(Model model) {
        List<JobApplication> applications = applicationService.getApplicationsByEngineer(CURRENT_ENGINEER_ID);
        model.addAttribute("applications", applications);
        return "Engineer/projects";
    }

    @GetMapping("/notifications")
    public String engineerNotifications(Model model) {
        List<Notification> notifications = notificationService.getUserNotifications(CURRENT_ENGINEER_ID);
        model.addAttribute("notifications", notifications);
        return "Engineer/notifications";
    }

    @GetMapping("/settings")
    public String engineerSettings() {
        return "Engineer/settings";
    }

    @PostMapping("/applications")
    public String applyToJob(@RequestParam Long jobId) {
        applicationService.applyToJob(jobId, CURRENT_ENGINEER_ID);
        return "redirect:/engineer/projects";
    }
}
