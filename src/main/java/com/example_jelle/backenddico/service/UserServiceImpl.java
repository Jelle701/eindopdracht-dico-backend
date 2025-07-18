// src/main/java/com/example_jelle/backenddico/service/UserServiceImpl.java
package com.example_jelle.backenddico.service;

import com.example_jelle.backenddico.dto.RegisterRequest;
import com.example_jelle.backenddico.dto.UserOutputDto;
import com.example_jelle.backenddico.exceptions.EmailAlreadyExists;
import com.example_jelle.backenddico.model.User;
import com.example_jelle.backenddico.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override // Deze annotatie is nu geldig
    @Transactional
    public void register(RegisterRequest req) {
        if (userRepository.findByEmail(req.getEmail()).isPresent()) {
            throw new EmailAlreadyExists("E-mailadres " + req.getEmail() + " is al in gebruik.");
        }

        User user = new User();
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setFirstName(req.getFirstName());
        user.setLastName(req.getLastName());
        user.setDob(req.getDob());
        user.setRole("ROLE_USER");

        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserOutputDto findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User niet gevonden"));
        return UserOutputDto.from(user);
    }
}