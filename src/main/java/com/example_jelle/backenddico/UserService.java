package com.example_jelle.backenddico.service;

import com.example_jelle.backenddico.dto.UserInputDto;
import com.example_jelle.backenddico.dto.UserOutputDto;
import com.example_jelle.backenddico.model.User;
import com.example_jelle.backenddico.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder; // Importeren van de interface is een best practice
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // Gebruik de interface voor flexibiliteit

    // Injecteer zowel de repository als de encoder via de constructor
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserOutputDto registerUser(UserInputDto inputDto) {
        User user = new User();
        user.setEmail(inputDto.getEmail());
        // Hash het wachtwoord voordat je het opslaat
        user.setPassword(passwordEncoder.encode(inputDto.getPassword()));
        user.setRole("USER");

        User saved = userRepository.save(user);

        return new UserOutputDto(saved.getId(), saved.getEmail(), saved.getRole());
    }
}