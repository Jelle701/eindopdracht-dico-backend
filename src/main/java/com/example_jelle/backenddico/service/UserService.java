// src/main/java/com/example_jelle/backenddico/service/UserService.java
package com.example_jelle.backenddico.service;

import com.example_jelle.backenddico.dto.RegisterRequest;
import com.example_jelle.backenddico.dto.UserOutputDto;
import com.example_jelle.backenddico.dto.onboarding.OnboardingRequestDto; // <-- Nieuwe import

public interface UserService {
    void register(RegisterRequest req);
    UserOutputDto findByEmail(String email);
    void saveOnboardingData(String userEmail, OnboardingRequestDto onboardingData); // <-- Nieuwe methode
}