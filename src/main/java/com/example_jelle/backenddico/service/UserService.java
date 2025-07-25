package com.example_jelle.backenddico.service;

import com.example_jelle.backenddico.dto.UserOutputDto;
import com.example_jelle.backenddico.dto.onboarding.OnboardingRequestDto;
import com.example_jelle.backenddico.model.User;
import com.example_jelle.backenddico.payload.request.RegisterRequest;

public interface UserService {
    void register(RegisterRequest req);
    User verifyUser(String email, String token); // Geeft User terug om JWT te genereren
    UserOutputDto findByEmail(String email);
    UserOutputDto saveProfileDetails(String userEmail, OnboardingRequestDto onboardingData);
}