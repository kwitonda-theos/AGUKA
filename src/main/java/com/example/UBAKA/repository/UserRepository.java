package com.example.UBAKA.repository;

import com.example.UBAKA.model.User;
import com.example.UBAKA.model.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByPhone(String phone);

    long countByRole(UserRole role);
}