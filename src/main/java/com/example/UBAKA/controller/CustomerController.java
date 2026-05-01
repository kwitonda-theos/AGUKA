package com.example.UBAKA.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @GetMapping("/dashboard")
    public String dashboard() {
        return "customer/dashboard";
    }

    @GetMapping("/my-jobs")
    public String myJobs() {
        return "customer/my-jobs";
    }

    @GetMapping("/post-job")
    public String postJob() {
        return "customer/post-job";
    }

    @GetMapping("/engineers")
    public String engineers() {
        return "customer/engineers";
    }

    @GetMapping("/notifications")
    public String notifications() {
        return "customer/notifications";
    }

}