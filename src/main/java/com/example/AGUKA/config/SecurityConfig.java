package com.example.AGUKA.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Disable CSRF for forms for now, or ensure Thymeleaf injects it
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authz -> authz
                // Allow public access to all auth routes and static assets
                .requestMatchers("/auth/**", "/assets/**", "/favicon.svg", "/icons/**").permitAll()
                // All other requests must be authenticated
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                // Use our custom login page
                .loginPage("/auth/login")
                .defaultSuccessUrl("/", true) // Redirect to home page on success
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/auth/logout")
                .logoutSuccessUrl("/auth/login?logout")
                .permitAll()
            );

        return http.build();
    }
}
