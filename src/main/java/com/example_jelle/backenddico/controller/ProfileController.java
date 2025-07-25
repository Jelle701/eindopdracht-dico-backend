package com.example_jelle.backenddico.controller;

import com.example_jelle.backenddico.dto.UserOutputDto;
// FIX: Voeg de benodigde imports toe
import com.example_jelle.backenddico.dto.onboarding.OnboardingRequestDto;
import com.example_jelle.backenddico.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*; // Importeer @PutMapping en @RequestBody

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<UserOutputDto> getUserProfile(Authentication authentication) {
        String userEmail = authentication.getName();
        UserOutputDto userProfile = userService.findByEmail(userEmail);
        return ResponseEntity.ok(userProfile);
    }

    /**
     * FIX: Voeg deze nieuwe methode toe.
     * Deze methode handelt de PUT request af om de onboarding-gegevens van een gebruiker op te slaan.
     *
     * @param authentication Het authenticatieobject om de gebruiker te identificeren.
     * @param onboardingData De DTO met de profiel- en apparaatgegevens.
     * @return Een ResponseEntity met de bijgewerkte gebruikersgegevens.
     */
    @PutMapping("/details")
    public ResponseEntity<UserOutputDto> saveOnboardingDetails(
            Authentication authentication,
            @Valid @RequestBody OnboardingRequestDto onboardingData) {

        String userEmail = authentication.getName();
        UserOutputDto updatedUser = userService.saveProfileDetails(userEmail, onboardingData);
        return ResponseEntity.ok(updatedUser);
    }
}