package com.example_jelle.backenddico.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example_jelle.backenddico.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}