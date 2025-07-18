// src/main/java/com/example_jelle/backenddico/service/UserServiceImpl.java
package com.example_jelle.backenddico.service;

import com.example_jelle.backenddico.dto.RegisterRequest;
import com.example_jelle.backenddico.dto.UserOutputDto;
import com.example_jelle.backenddico.dto.onboarding.DeviceDto;
import com.example_jelle.backenddico.dto.onboarding.OnboardingRequestDto;
import com.example_jelle.backenddico.exceptions.EmailAlreadyExists;
import com.example_jelle.backenddico.model.User;
import com.example_jelle.backenddico.model.UserDevice;
import com.example_jelle.backenddico.model.UserProfile;
import com.example_jelle.backenddico.repository.UserDeviceRepository;
import com.example_jelle.backenddico.repository.UserProfileRepository;
import com.example_jelle.backenddico.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository; // <-- Nieuwe dependency
    private final UserDeviceRepository userDeviceRepository; // <-- Nieuwe dependency
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserProfileRepository userProfileRepository, UserDeviceRepository userDeviceRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
        this.userDeviceRepository = userDeviceRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
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

    // --- NIEUWE IMPLEMENTATIE ---
    @Override
    @Transactional
    public void saveOnboardingData(String userEmail, OnboardingRequestDto onboardingData) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("Gebruiker niet gevonden voor onboarding."));

        // Update de rol van de gebruiker
        user.setRole(onboardingData.getRole());

        // Maak en sla het gebruikersprofiel op
        UserProfile profile = new UserProfile();
        profile.setUser(user);
        profile.setMeasurementSystem(onboardingData.getPreferences().getSystem());
        profile.setGender(onboardingData.getPreferences().getGender());
        profile.setWeight(Double.parseDouble(onboardingData.getPreferences().getWeight()));
        profile.setHeight(Double.parseDouble(onboardingData.getPreferences().getHeight()));
        profile.setBmi(Double.parseDouble(onboardingData.getPreferences().getBmi()));
        userProfileRepository.save(profile);

        // Maak en sla de apparaten op
        for (DeviceDto deviceDto : onboardingData.getDiabeticDevices()) {
            UserDevice userDevice = new UserDevice();
            userDevice.setUser(user);
            userDevice.setCategory(deviceDto.getCategorie());
            userDevice.setManufacturer(deviceDto.getFabrikant());
            userDevice.setModel(deviceDto.getModel());
            userDeviceRepository.save(userDevice);
        }

        // Sla de geüpdatete gebruiker op (vooral voor de rol)
        userRepository.save(user);
    }
}