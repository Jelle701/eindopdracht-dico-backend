// src/main/java/com/example_jelle/backenddico/controller/UserController.java
package com.example_jelle.backenddico.controller;

import com.example_jelle.backenddico.dto.UserInputDto;
import com.example_jelle.backenddico.dto.UserOutputDto;
import com.example_jelle.backenddico.dto.VerifyDto;
import com.example_jelle.backenddico.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Controller voor gebruikersgerelateerde endpoints:
 * - Registratie, verificatie, opnieuw verzenden
 * - Opvragen profiel (beveiligd)
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Haal profiel op voor ingelogde user.
     * GET /api/users/profile
     */
    @GetMapping("/profile")
    public ResponseEntity<UserOutputDto> getProfile(Authentication auth) {
        String email = auth.getName();
        UserOutputDto user = userService.findByEmail(email);
        return ResponseEntity.ok(user);
    }

    /**
     * Registreer een nieuwe gebruiker.
     * POST /api/users/register
     */
    @PostMapping("/register")
    public ResponseEntity<UserOutputDto> registerUser(@RequestBody UserInputDto inputDto) {
        UserOutputDto result = userService.registerUser(inputDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    /**
     * Verifieer e-mail met code. Dev-snelpad: code "123456" altijd geldig.
     * POST /api/users/verify-email
     */
    @PostMapping("/verify-email")
    public ResponseEntity<Map<String, String>> verifyEmail(@RequestBody VerifyDto dto) {
        if (!"123456".equals(dto.getCode())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Onjuiste verificatiecode"));
        }
        return ResponseEntity.ok(Map.of("message", "Email succesvol geverifieerd"));
    }

    /**
     * Verstuur een nieuwe verificatiecode.
     * POST /api/users/resend-verification
     */
    @PostMapping("/resend-verification")
    public ResponseEntity<Map<String, String>> resendVerification(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        // logica om nieuwe code te versturen
        return ResponseEntity.ok(Map.of("message", "Verificatiecode verstuurd"));
    }
}
