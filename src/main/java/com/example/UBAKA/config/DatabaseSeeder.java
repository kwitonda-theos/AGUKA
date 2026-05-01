package com.example.UBAKA.config;

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
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class DatabaseSeeder {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository,
                                   CustomerRepository customerRepository,
                                   EngineerRepository engineerRepository) {
        return args -> {
            // Check if data already exists to avoid duplicate entries on restart
            if (userRepository.count() == 0) {
                
                // 1. Create a Customer User
                User customerUser = new User();
                customerUser.setFullName("John Doe (Customer)");
                customerUser.setEmail("customer@example.com");
                customerUser.setPhone("0781234567");
                customerUser.setPassword("password123");
                customerUser.setRole(UserRole.CUSTOMER);
                customerUser.setAccountStatus(AccountStatus.ACTIVE);
                userRepository.save(customerUser);

                // 2. Create the Customer entity linked to the User
                Customer customer = new Customer();
                customer.setUser(customerUser);
                customer.setLocation("Kigali");
                customerRepository.save(customer);

                // 3. Create an Engineer User
                User engineerUser = new User();
                engineerUser.setFullName("Jane Smith (Engineer)");
                engineerUser.setEmail("engineer@example.com");
                engineerUser.setPhone("0787654321");
                engineerUser.setPassword("password123");
                engineerUser.setRole(UserRole.ENGINEER);
                engineerUser.setAccountStatus(AccountStatus.ACTIVE);
                userRepository.save(engineerUser);

                // 4. Create the Engineer entity linked to the User
                Engineer engineer = new Engineer();
                engineer.setUser(engineerUser);
                engineer.setSpecialization("Plumbing");
                engineer.setExperienceYears(5);
                engineer.setLocation("Kigali");
                engineer.setBio("Expert in fixing all kinds of plumbing issues.");
                engineer.setVerificationStatus(VerificationStatus.APPROVED);
                engineer.setAvailabilityStatus(AvailabilityStatus.AVAILABLE);
                engineerRepository.save(engineer);

                System.out.println("✅ Database seeded with initial Customer and Engineer!");
            } else {
                System.out.println("✅ Database already contains data. Skipping initial seeding.");
            }
        };
    }
}
