package com.example_jelle.backenddico.repository;

import com.example_jelle.backenddico.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // This repository only needs these two methods for the current application logic.
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    // The findByVerificationCode method can be safely removed as it is no longer called by any service.
}