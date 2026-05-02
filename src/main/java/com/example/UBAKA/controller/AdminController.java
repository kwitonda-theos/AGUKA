package com.example.UBAKA.controller;

import com.example.UBAKA.model.Engineer;
import com.example.UBAKA.model.Job;
import com.example.UBAKA.service.JobService;
import com.example.UBAKA.service.MatchingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final JobService jobService;
    private final MatchingService matchingService;

    public AdminController(JobService jobService, MatchingService matchingService) {
        this.jobService = jobService;
        this.matchingService = matchingService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // Basic admin dashboard
        return "admin/dashboard";
    }

    @GetMapping("/jobs/open")
    public String openJobs(Model model) {
        List<Job> openJobs = jobService.getOpenJobs();
        model.addAttribute("jobs", openJobs);
        return "admin/open-jobs";
    }

    @GetMapping("/matching/job/{jobId}")
    public String findMatchingEngineers(@PathVariable Long jobId, Model model) {
        List<Engineer> engineers = matchingService.findMatchingEngineers(jobId);
        model.addAttribute("engineers", engineers);
        model.addAttribute("jobId", jobId);
        return "admin/matching";
    }

    @PostMapping("/matching/job/{jobId}/notify")
    public String matchAndNotify(@PathVariable Long jobId) {
        matchingService.matchAndNotify(jobId);
        return "redirect:/admin/jobs/open"; // Redirect to open jobs after matching
    }
}
