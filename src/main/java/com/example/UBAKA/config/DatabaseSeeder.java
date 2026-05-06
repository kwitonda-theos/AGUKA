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
import jakarta.persistence.EntityManager;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

@Configuration
public class DatabaseSeeder {

    @Bean
    @Transactional
    CommandLineRunner initDatabase(UserRepository userRepository,
                                   CustomerRepository customerRepository,
                                   EngineerRepository engineerRepository,
                                   EntityManager entityManager) {
        return args -> {
            // Data Migration: Fix old 'APPROVED' status to 'VERIFIED'
            try {
                entityManager.createNativeQuery(
                        "UPDATE engineers SET verification_status = 'VERIFIED' WHERE verification_status = 'APPROVED'"
                ).executeUpdate();
                System.out.println("✅ Data migration: 'APPROVED' statuses updated to 'VERIFIED'.");
            } catch (Exception e) {
                System.out.println("ℹ️ No migration needed or table not yet created.");
            }

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
                engineer.setVerificationStatus(VerificationStatus.VERIFIED);
                engineer.setAvailabilityStatus(AvailabilityStatus.AVAILABLE);
                engineerRepository.save(engineer);

                // 5. Create an Admin User
                User adminUser = new User();
                adminUser.setFullName("Platform Administrator");
                adminUser.setEmail("admin@aguka.com");
                adminUser.setPhone("0780000000");
                adminUser.setPassword("adminpassword");
                adminUser.setRole(UserRole.ADMIN);
                adminUser.setAccountStatus(AccountStatus.ACTIVE);
                userRepository.save(adminUser);

                System.out.println("✅ Database seeded with initial Customer, Engineer, and Admin!");
            } else {
                System.out.println("✅ Database already contains data. Skipping initial seeding.");
            }

            // Ensure at least one Admin exists
            if (userRepository.findByEmail("admin@aguka.com").isEmpty()) {
                User adminUser = new User();
                adminUser.setFullName("Platform Administrator");
                adminUser.setEmail("admin@aguka.com");
                adminUser.setPhone("0780000000");
                adminUser.setPassword("adminpassword");
                adminUser.setRole(UserRole.ADMIN);
                adminUser.setAccountStatus(AccountStatus.ACTIVE);
                userRepository.save(adminUser);
                System.out.println("✅ Default Admin user created!");
            }
        };
    }
}
