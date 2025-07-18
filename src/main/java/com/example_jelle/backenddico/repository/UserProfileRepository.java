// src/main/java/com/example_jelle/backenddico/repository/UserProfileRepository.java
package com.example_jelle.backenddico.repository;

import com.example_jelle.backenddico.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
}