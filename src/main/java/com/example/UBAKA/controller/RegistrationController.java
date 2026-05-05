package com.example.UBAKA.controller;

import com.example.UBAKA.model.Customer;
import com.example.UBAKA.model.Engineer;
import com.example.UBAKA.model.User;
import com.example.UBAKA.model.enums.AccountStatus;
import com.example.UBAKA.model.enums.AvailabilityStatus;
import com.example.UBAKA.model.enums.UserRole;
import com.example.UBAKA.model.enums.VerificationStatus;
import com.example.UBAKA.repository.CustomerRepository;
import com.example.UBAKA.repository.EngineerRepository;
import com.example.UBAKA.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final EngineerRepository engineerRepository;

    public RegistrationController(UserRepository userRepository,
                                  CustomerRepository customerRepository,
                                  EngineerRepository engineerRepository) {
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
        this.engineerRepository = engineerRepository;
    }

    // ─────────────────────────────────────────────────────────────────
    //  CLIENT registration
    //  Form fields: email, fullName, phone, password, confirmPassword
    // ─────────────────────────────────────────────────────────────────
    @PostMapping("/client")
    public String registerClient(
            @RequestParam String email,
            @RequestParam String fullName,
            @RequestParam(required = false) String phone,
            @RequestParam String password,
            @RequestParam String confirmPassword,
            Model model) {

        // 1. Validate passwords match
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match.");
            return "auth/client";
        }

        // 2. Check email and phone not already taken
        if (userRepository.findByEmail(email).isPresent()) {
            model.addAttribute("error", "An account with that email already exists.");
            return "auth/client";
        }
        if (phone != null && !phone.trim().isEmpty() && userRepository.findByPhone(phone).isPresent()) {
            model.addAttribute("error", "An account with that phone number already exists.");
            return "auth/client";
        }

        // 3. Build and save User
        User user = new User();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPhone(phone);
        user.setPassword(password);          // plain-text for now; hash later
        user.setRole(UserRole.CUSTOMER);
        user.setAccountStatus(AccountStatus.ACTIVE);
        userRepository.save(user);

        // 4. Build and save Customer profile
        Customer customer = new Customer();
        customer.setUser(user);
        customerRepository.save(customer);

        // 5. Redirect to login with a success hint
        return "redirect:/auth/login?registered";
    }

    // ─────────────────────────────────────────────────────────────────
    //  ENGINEER registration
    //  Form fields: email, profession, fullName, experience, phone,
    //               location, password, confirmPassword
    // ─────────────────────────────────────────────────────────────────
    @PostMapping("/engineer")
    public String registerEngineer(
            @RequestParam String email,
            @RequestParam String fullName,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String profession,
            @RequestParam(required = false, defaultValue = "0") Integer experience,
            @RequestParam(required = false) String location,
            @RequestParam String password,
            @RequestParam String confirmPassword,
            Model model) {

        // 1. Validate passwords match
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match.");
            return "auth/engineer";
        }

        // 2. Check email and phone not already taken
        if (userRepository.findByEmail(email).isPresent()) {
            model.addAttribute("error", "An account with that email already exists.");
            return "auth/engineer";
        }
        if (phone != null && !phone.trim().isEmpty() && userRepository.findByPhone(phone).isPresent()) {
            model.addAttribute("error", "An account with that phone number already exists.");
            return "auth/engineer";
        }

        // 3. Build and save User
        User user = new User();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPhone(phone);
        user.setPassword(password);          // plain-text for now; hash later
        user.setRole(UserRole.ENGINEER);
        user.setAccountStatus(AccountStatus.ACTIVE);
        userRepository.save(user);

        // 4. Build and save Engineer profile
        Engineer engineer = new Engineer();
        engineer.setUser(user);
        engineer.setSpecialization(profession != null ? profession : "General");
        engineer.setExperienceYears(experience);
        engineer.setLocation(location);
        engineer.setVerificationStatus(VerificationStatus.PENDING);
        engineer.setAvailabilityStatus(AvailabilityStatus.AVAILABLE);
        engineer.setAverageRating(0.0);
        engineer.setTotalReviews(0);
        engineerRepository.save(engineer);

        // 5. Redirect to login with a success hint
        return "redirect:/auth/login?registered";
    }
}
