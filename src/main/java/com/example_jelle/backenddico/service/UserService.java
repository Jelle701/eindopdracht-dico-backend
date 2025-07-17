// src/main/java/com/example_jelle/backenddico/service/UserService.java
package com.example_jelle.backenddico.service;

import com.example_jelle.backenddico.dto.UserInputDto;
import com.example_jelle.backenddico.dto.UserOutputDto;
import com.example_jelle.backenddico.model.User;
import com.example_jelle.backenddico.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

/**
 * Service voor gebruiker-gerelateerde businesslogica.
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Registreert een nieuwe gebruiker en retourneert output DTO.
     */
    public UserOutputDto registerUser(UserInputDto input) {
        User user = new User();
        user.setFirstName(input.getFirstName());
        user.setLastName(input.getLastName());
        user.setEmail(input.getEmail());
        user.setDob(input.getDob());
        user.setPassword(input.getPassword());
        user.setRole("USER");
        User saved = userRepository.save(user);

        // Gebruik setter-based DTO creation
        UserOutputDto dto = new UserOutputDto();
        dto.setId(saved.getId());
        dto.setEmail(saved.getEmail());
        dto.setFirstName(saved.getFirstName());
        dto.setLastName(saved.getLastName());
        dto.setDob(saved.getDob().toString());
        dto.setRole(saved.getRole());
        return dto;
    }

    /**
     * Haalt een bestaande gebruiker op via e-mail en converteert naar DTO.
     */
    public UserOutputDto findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("Gebruiker niet gevonden: " + email));
        UserOutputDto dto = new UserOutputDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setDob(user.getDob().toString());
        dto.setRole(user.getRole());
        return dto;
    }
}
