package com.example.UBAKA.config;

import com.example.UBAKA.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    // Plain-text encoder for now — hash passwords later
    @Bean
    @SuppressWarnings("deprecation")
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authenticationProvider(authenticationProvider())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authz -> authz
                        // Public resources
                        .requestMatchers(
                                "/", "/auth/**", "/register/**",
                                "/assets/**", "/favicon.svg", "/icons/**",
                                "/uploads/**",
                                "/error", "/*.css", "/*.js", "/*.svg", "/*.png")
                        .permitAll()
                        // Role-protected areas
                        .requestMatchers("/customer/**").hasAnyRole("CUSTOMER", "ADMIN")
                        .requestMatchers("/engineer/**").hasAnyRole("ENGINEER", "ADMIN")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/auth/login") // POST endpoint Spring Security handles
                        .defaultSuccessUrl("/auth/redirect-after-login", true)
                        .failureUrl("/auth/login?error")
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/auth/logout")
                        .logoutSuccessUrl("/auth/login?logout")
                        .permitAll()
                // )
                // .exceptionHandling(ex -> ex
                // .accessDeniedHandler((request, response, accessDeniedException) -> {
                // // Invalidates corrupted sessions caused by DevTools hot-reloads
                // // and forces the user back to the login page instead of throwing a 403.
                // if (request.getSession(false) != null) {
                // request.getSession().invalidate();
                // }
                // response.sendRedirect("/auth/login?expired");
                // })
                );

        return http.build();
    }
}
