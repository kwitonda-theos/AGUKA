package com.example.UBAKA.controller;

import com.example.UBAKA.model.Customer;
import com.example.UBAKA.model.Job;
import com.example.UBAKA.model.JobApplication;
import com.example.UBAKA.model.Notification;
import com.example.UBAKA.model.User;
import com.example.UBAKA.model.enums.JobStatus;
import com.example.UBAKA.repository.UserRepository;
import com.example.UBAKA.service.ApplicationService;
import com.example.UBAKA.service.JobService;
import com.example.UBAKA.service.NotificationService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/customer")
public class CustomerDashboardController {

    private final JobService jobService;
    private final ApplicationService applicationService;
    private final NotificationService notificationService;
    private final UserRepository userRepository;

    public CustomerDashboardController(JobService jobService,
                                       ApplicationService applicationService,
                                       NotificationService notificationService,
                                       UserRepository userRepository) {
        this.jobService = jobService;
        this.applicationService = applicationService;
        this.notificationService = notificationService;
        this.userRepository = userRepository;
    }

    /** Resolves the logged-in user's Customer record from the security context. */
    private Customer getCustomer(Authentication auth) {
        String email = auth.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found: " + email));
        return user.getCustomer();
    }

    @GetMapping("/dashboard")
    public String dashboard(Authentication auth, Model model) {
        Customer customer = getCustomer(auth);
        if (customer == null) return "redirect:/auth/login";

        model.addAttribute("customerName", customer.getUser().getFullName());

        List<Job> jobs = jobService.getJobsByCustomer(customer.getId());

        long totalJobs = jobs.size();
        long activeJobs = jobs.stream()
                .filter(j -> j.getStatus() == JobStatus.OPEN
                        || j.getStatus() == JobStatus.MATCHED
                        || j.getStatus() == JobStatus.IN_PROGRESS
                        || j.getStatus() == JobStatus.ASSIGNED)
                .count();
        long completedJobs = jobs.stream()
                .filter(j -> j.getStatus() == JobStatus.COMPLETED)
                .count();

        List<Job> recentJobs = jobs.stream()
                .sorted((j1, j2) -> j2.getCreatedAt().compareTo(j1.getCreatedAt()))
                .limit(5)
                .collect(Collectors.toList());

        model.addAttribute("totalJobs", totalJobs);
        model.addAttribute("activeJobs", activeJobs);
        model.addAttribute("completedJobs", completedJobs);
        model.addAttribute("recentJobs", recentJobs);

        return "customer/dashboard";
    }

    @GetMapping("/my-jobs")
    public String myJobs(Authentication auth, Model model) {
        Customer customer = getCustomer(auth);
        if (customer == null) return "redirect:/auth/login";

        List<Job> jobs = jobService.getJobsByCustomer(customer.getId());
        model.addAttribute("jobs", jobs);
        return "customer/my-jobs";
    }

    @GetMapping("/post-job")
    public String showPostJobForm(Model model) {
        model.addAttribute("job", new Job());
        return "customer/post-job";
    }

    @PostMapping("/post-job")
    public String submitPostJob(@ModelAttribute Job job, Authentication auth) {
        Customer customer = getCustomer(auth);
        if (customer == null) return "redirect:/auth/login";

        jobService.createJob(job, customer.getId());
        return "redirect:/customer/my-jobs";
    }

    @GetMapping("/engineers")
    public String engineers(@RequestParam(required = false) Long jobId, Authentication auth, Model model) {
        Customer customer = getCustomer(auth);
        if (customer == null) return "redirect:/auth/login";

        if (jobId != null) {
            Job job = jobService.getJobById(jobId).orElse(null);
            if (job != null) {
                model.addAttribute("selectedJob", job);
                List<JobApplication> applications = applicationService.getApplicationsByJob(jobId);
                model.addAttribute("applications", applications);
            }
        }

        List<Job> customerJobs = jobService.getJobsByCustomer(customer.getId());
        model.addAttribute("customerJobs", customerJobs);

        return "customer/engineers";
    }

    @GetMapping("/notifications")
    public String notifications(Authentication auth, Model model) {
        Customer customer = getCustomer(auth);
        if (customer == null) return "redirect:/auth/login";

        // Notifications are linked to User, not Customer — use user ID
        Long userId = customer.getUser().getId();
        List<Notification> notifications = notificationService.getUserNotifications(userId);
        model.addAttribute("notifications", notifications);
        return "customer/notifications";
    }

    @PostMapping("/applications/{applicationId}/accept")
    public String acceptApplication(@PathVariable Long applicationId) {
        applicationService.acceptApplication(applicationId);
        return "redirect:/customer/my-jobs";
    }

    @PostMapping("/applications/{applicationId}/decline")
    public String declineApplication(@PathVariable Long applicationId) {
        applicationService.declineApplication(applicationId);
        return "redirect:/customer/my-jobs";
    }

    @PostMapping("/jobs/{jobId}/status")
    public String updateJobStatus(@PathVariable Long jobId, @RequestParam JobStatus status) {
        jobService.updateJobStatus(jobId, status);
        return "redirect:/customer/my-jobs";
    }

    @PostMapping("/jobs/{jobId}/agree-finish")
    public String agreeToFinishJob(@PathVariable Long jobId, Authentication auth) {
        Customer customer = getCustomer(auth);
        if (customer == null) return "redirect:/auth/login";

        jobService.markJobAsAgreedToFinish(jobId, true);
        return "redirect:/customer/my-jobs";
    }
}
