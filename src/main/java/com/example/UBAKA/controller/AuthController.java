package com.example.UBAKA.controller;

import com.example.UBAKA.model.User;
import com.example.UBAKA.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    /** Called after successful login — reads role and redirects to the correct dashboard. */
    @GetMapping("/redirect-after-login")
    public String redirectAfterLogin(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/auth/login";
        }

        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) return "redirect:/auth/login?error";

        return switch (user.getRole()) {
            case CUSTOMER -> "redirect:/customer/dashboard";
            case ENGINEER -> "redirect:/engineer/dashboard";
            case ADMIN    -> "redirect:/admin/dashboard";
        };
    }

    @GetMapping("/role-confirmation")
    public String roleConfirmation() {
        return "auth/role-confirmation";
    }

    @GetMapping("/client")
    public String clientSignup() {
        return "auth/client";
    }

    @GetMapping("/engineer")
    public String engineerSignup() {
        return "auth/engineer";
    }

    @GetMapping("/verify")
    public String verifyAccount() {
        return "auth/verify";
    }

    @GetMapping("/reset-password")
    public String resetPassword() {
        return "auth/reset-password";
    }
}
