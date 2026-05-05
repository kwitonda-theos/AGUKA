package com.example.UBAKA.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LandingPageController {

    @GetMapping("/")
    public String home() {
        return "index"; // resolves to templates/index.html
    }
}
