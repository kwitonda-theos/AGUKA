package com.example.UBAKA.controller;

import com.example.UBAKA.model.Customer;
import com.example.UBAKA.model.Job;
import com.example.UBAKA.model.JobApplication;
import com.example.UBAKA.model.Notification;
import com.example.UBAKA.model.enums.JobStatus;
import com.example.UBAKA.repository.CustomerRepository;
import com.example.UBAKA.service.ApplicationService;
import com.example.UBAKA.service.JobService;
import com.example.UBAKA.service.MatchingService;
import com.example.UBAKA.service.NotificationService;
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
    private final MatchingService matchingService;
    private final CustomerRepository customerRepository;

    // Hardcoded for now, would typically come from Spring Security Context
    private final Long CURRENT_CUSTOMER_ID = 1L;

    public CustomerDashboardController(JobService jobService,
                                       ApplicationService applicationService,
                                       NotificationService notificationService,
                                       MatchingService matchingService,
                                       CustomerRepository customerRepository) {
        this.jobService = jobService;
        this.applicationService = applicationService;
        this.notificationService = notificationService;
        this.matchingService = matchingService;
        this.customerRepository = customerRepository;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        Customer customer = customerRepository.findById(CURRENT_CUSTOMER_ID).orElse(null);
        if (customer != null && customer.getUser() != null) {
            model.addAttribute("customerName", customer.getUser().getFullName());
        } else {
            model.addAttribute("customerName", "Customer");
        }

        List<Job> jobs = jobService.getJobsByCustomer(CURRENT_CUSTOMER_ID);
        
        long totalJobs = jobs.size();
        long activeJobs = jobs.stream()
                .filter(j -> j.getStatus() == JobStatus.OPEN || j.getStatus() == JobStatus.MATCHED || j.getStatus() == JobStatus.IN_PROGRESS || j.getStatus() == JobStatus.ASSIGNED)
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
    public String myJobs(Model model) {
        List<Job> jobs = jobService.getJobsByCustomer(CURRENT_CUSTOMER_ID);
        model.addAttribute("jobs", jobs);
        return "customer/my-jobs";
    }

    @GetMapping("/post-job")
    public String showPostJobForm(Model model) {
        model.addAttribute("job", new Job());
        return "customer/post-job";
    }

    @PostMapping("/post-job")
    public String submitPostJob(@ModelAttribute Job job) {
        jobService.createJob(job, CURRENT_CUSTOMER_ID);
        return "redirect:/customer/my-jobs";
    }

    @GetMapping("/engineers")
    public String engineers(@RequestParam(required = false) Long jobId, Model model) {
        if (jobId != null) {
            Job job = jobService.getJobById(jobId).orElse(null);
            if (job != null) {
                model.addAttribute("selectedJob", job);
                List<JobApplication> applications = applicationService.getApplicationsByJob(jobId);
                model.addAttribute("applications", applications);
            }
        }
        
        // Also add list of customer's jobs so they can select one to see matched engineers
        List<Job> customerJobs = jobService.getJobsByCustomer(CURRENT_CUSTOMER_ID);
        model.addAttribute("customerJobs", customerJobs);
        
        return "customer/engineers";
    }

    @GetMapping("/notifications")
    public String notifications(Model model) {
        List<Notification> notifications = notificationService.getUserNotifications(CURRENT_CUSTOMER_ID);
        model.addAttribute("notifications", notifications);
        return "customer/notifications";
    }

    @PostMapping("/applications/{applicationId}/accept")
    public String acceptApplication(@PathVariable Long applicationId) {
        applicationService.acceptApplication(applicationId);
        return "redirect:/customer/my-jobs"; // Or another appropriate redirect
    }

    @PostMapping("/applications/{applicationId}/decline")
    public String declineApplication(@PathVariable Long applicationId) {
        applicationService.declineApplication(applicationId);
        return "redirect:/customer/my-jobs"; // Or another appropriate redirect
    }

    @PostMapping("/jobs/{jobId}/status")
    public String updateJobStatus(@PathVariable Long jobId, @RequestParam JobStatus status) {
        jobService.updateJobStatus(jobId, status);
        return "redirect:/customer/my-jobs";
    }
}
