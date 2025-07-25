package com.example_jelle.backenddico.service;

import com.example_jelle.backenddico.dto.FullUserProfileDto;
import com.example_jelle.backenddico.dto.UserOutputDto;
import com.example_jelle.backenddico.dto.onboarding.OnboardingRequestDto;
import com.example_jelle.backenddico.model.User;
import com.example_jelle.backenddico.payload.request.RegisterRequest;

public interface UserService {
    void register(RegisterRequest req);
    User verifyUser(String email, String token);
    UserOutputDto findByEmail(String email);

    /**
     * Slaat de profiel- en apparaatgegevens van een gebruiker op.
     * @return De volledige, bijgewerkte profielinformatie.
     */
    FullUserProfileDto saveProfileDetails(String userEmail, OnboardingRequestDto onboardingData);

    /**
     * Haalt de volledige profielinformatie op voor de opgegeven gebruiker.
     * @return Een DTO met alle profielgegevens, voorkeuren en apparaten.
     */
    FullUserProfileDto getFullUserProfile(String email);
}