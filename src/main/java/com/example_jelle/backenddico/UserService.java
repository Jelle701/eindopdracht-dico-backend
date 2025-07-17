package com.example_jelle.backenddico.service;

import com.example_jelle.backenddico.dto.UserInputDto;
import com.example_jelle.backenddico.dto.UserOutputDto;
import com.example_jelle.backenddico.exceptions.EmailAlreadyExists;
import com.example_jelle.backenddico.model.User;
import com.example_jelle.backenddico.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserOutputDto registerUser(UserInputDto inputDto) {
        // Controleer of de email al bestaat
        if (userRepository.findByEmail(inputDto.getEmail()).isPresent()) {
            throw new EmailAlreadyExists("E-mailadres is al in gebruik.");
        }

        User user = new User();
        user.setEmail(inputDto.getEmail());
        user.setPassword(passwordEncoder.encode(inputDto.getPassword()));
        user.setRole("USER");

        User saved = userRepository.save(user);

        return new UserOutputDto(saved.getId(), saved.getEmail(), saved.getRole());
    }
}