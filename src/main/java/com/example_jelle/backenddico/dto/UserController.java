// src/main/java/com/example_jelle/backenddico/controller/UserController.java
package com.example_jelle.backenddico.controller;

import com.example_jelle.backenddico.dto.UserInputDto;
import com.example_jelle.backenddico.dto.UserOutputDto;
import com.example_jelle.backenddico.dto.VerifyDto;
import com.example_jelle.backenddico.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserOutputDto> registerUser(@RequestBody UserInputDto inputDto) {
        UserOutputDto result = userService.registerUser(inputDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    /**
     * Verifieert de code "123456" altijd en retourneert een message.
     * POST /api/users/verify-email
     */
    @PostMapping("/verify-email")
    public ResponseEntity<Map<String,String>> verifyEmail(@RequestBody VerifyDto dto) {
        // Dev-snelpad: code “123456” altijd geldig
        if (!"123456".equals(dto.getCode())) {
            // hier kun je echte logica toevoegen
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Onjuiste verificatiecode"));
        }
        return ResponseEntity.ok(Map.of("message", "Email succesvol geverifieerd"));
    }

    /**
     * Stuur een nieuwe verificatiecode naar email.
     * POST /api/users/resend-verification
     */
    @PostMapping("/resend-verification")
    public ResponseEntity<Map<String,String>> resendVerification(@RequestBody Map<String,String> body) {
        String email = body.get("email");
        // hier normaal je logica om een nieuwe code te versturen
        return ResponseEntity.ok(Map.of("message", "Verificatiecode verstuurd"));
    }
}
