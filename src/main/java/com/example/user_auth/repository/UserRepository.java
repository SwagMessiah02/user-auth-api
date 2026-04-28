package com.example.user_auth.repository;

import com.example.user_auth.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User,UUID> {
    Optional<User> findUserByEmail(String email);
}