package com.example.AGUKA.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/login")
    public String login() {
        // Will resolve to src/main/resources/templates/auth/login.html using Thymeleaf
        return "auth/login";
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
