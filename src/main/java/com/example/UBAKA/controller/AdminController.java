package com.example.UBAKA.controller;

import com.example.UBAKA.exception.ResourceNotFoundException;
import com.example.UBAKA.model.Engineer;
import com.example.UBAKA.model.User;
import com.example.UBAKA.model.enums.AccountStatus;
import com.example.UBAKA.model.enums.NotificationType;
import com.example.UBAKA.model.enums.UserRole;
import com.example.UBAKA.model.enums.VerificationStatus;
import com.example.UBAKA.repository.AssignmentRepository;
import com.example.UBAKA.repository.EngineerRepository;
import com.example.UBAKA.repository.JobRepository;
import com.example.UBAKA.repository.UserRepository;
import com.example.UBAKA.service.NotificationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final EngineerRepository engineerRepository;
    private final UserRepository userRepository;
    private final JobRepository jobRepository;
    private final AssignmentRepository assignmentRepository;
    private final NotificationService notificationService;

    public AdminController(EngineerRepository engineerRepository,
                           UserRepository userRepository,
                           JobRepository jobRepository,
                           AssignmentRepository assignmentRepository,
                           NotificationService notificationService) {
        this.engineerRepository = engineerRepository;
        this.userRepository = userRepository;
        this.jobRepository = jobRepository;
        this.assignmentRepository = assignmentRepository;
        this.notificationService = notificationService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalUsers", userRepository.count());
        model.addAttribute("totalEngineers", userRepository.countByRole(UserRole.ENGINEER));
        model.addAttribute("totalCustomers", userRepository.countByRole(UserRole.CUSTOMER));
        model.addAttribute("totalJobs", jobRepository.count());
        model.addAttribute("totalAssignments", assignmentRepository.count());
        model.addAttribute("pendingVerifications", engineerRepository.countByVerificationStatus(VerificationStatus.PENDING));
        return "admin/dashboard";
    }

    @GetMapping("/engineers")
    public String viewEngineers(Model model) {
        model.addAttribute("engineers", engineerRepository.findAll());
        return "admin/engineers";
    }

    @PostMapping("/engineers/approve")
    public String approveEngineer(@RequestParam Long engineerId) {
        Engineer engineer = engineerRepository.findById(engineerId)
                .orElseThrow(() -> new ResourceNotFoundException("Engineer not found"));
        engineer.setVerificationStatus(VerificationStatus.VERIFIED);
        engineerRepository.save(engineer);

        notificationService.createNotification(
                engineer.getUser().getId(),
                "Account Verified",
                "Your account has been verified",
                NotificationType.ACCOUNT_VERIFIED
        );

        return "redirect:/admin/engineers";
    }

    @PostMapping("/engineers/reject")
    public String rejectEngineer(@RequestParam Long engineerId) {
        Engineer engineer = engineerRepository.findById(engineerId)
                .orElseThrow(() -> new ResourceNotFoundException("Engineer not found"));
        engineer.setVerificationStatus(VerificationStatus.REJECTED);
        engineerRepository.save(engineer);

        notificationService.createNotification(
                engineer.getUser().getId(),
                "Verification Rejected",
                "Your verification request was rejected",
                NotificationType.ACCOUNT_REJECTED
        );

        return "redirect:/admin/engineers";
    }

    @GetMapping("/users")
    public String viewUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "admin/users";
    }

    @GetMapping("/jobs")
    public String viewJobs(Model model) {
        model.addAttribute("jobs", jobRepository.findAll());
        return "admin/jobs";
    }

    @GetMapping("/assignments")
    public String viewAssignments(Model model) {
        model.addAttribute("assignments", assignmentRepository.findAll());
        return "admin/assignments";
    }

    @PostMapping("/users/disable")
    public String disableUser(@RequestParam Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setAccountStatus(AccountStatus.DISABLED);
        userRepository.save(user);
        return "redirect:/admin/users";
    }
}
