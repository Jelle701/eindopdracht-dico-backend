// src/main/java/com/example_jelle/backenddico/controller/UserController.java
package com.example_jelle.backenddico.controller;

import com.example_jelle.backenddico.dto.RegisterRequest;
import com.example_jelle.backenddico.dto.UserOutputDto;
import com.example_jelle.backenddico.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    // Deze aanroep is nu geldig
    public ResponseEntity<Void> register(@RequestBody RegisterRequest req) {
        userService.register(req);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{email}")
                .buildAndExpand(req.getEmail()).toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/profile")
    public ResponseEntity<UserOutputDto> getProfile(Authentication auth) {
        String email = auth.getName();
        UserOutputDto dto = userService.findByEmail(email);
        return ResponseEntity.ok(dto);
    }
}