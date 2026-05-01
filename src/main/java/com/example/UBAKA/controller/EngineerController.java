package com.example.UBAKA.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EngineerController {

    @GetMapping("/auth/engineer-verification")
    public String engineerVerification() {
        return "Engineer/verification";
    }

    @GetMapping({"/engineer/dashboard", "/auth/engineer-dashboard"})
    public String engineerDashboard() {
        return "Engineer/dashboard";
    }

    @GetMapping({"/engineer/projects", "/auth/engineer-projects"})
    public String engineerProjects() {
        return "Engineer/projects";
    }

    @GetMapping({"/engineer/notifications", "/auth/engineer-notifications"})
    public String engineerNotifications() {
        return "Engineer/notifications";
    }

    @GetMapping({"/engineer/settings", "/auth/engineer-settings"})
    public String engineerSettings() {
        return "Engineer/settings";
    }
}
