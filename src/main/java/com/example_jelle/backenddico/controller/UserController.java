package com.example_jelle.backenddico.controller;

import com.example_jelle.backenddico.dto.RegisterRequest;
import com.example_jelle.backenddico.dto.UserOutputDto;
import com.example_jelle.backenddico.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterRequest req) {
        userService.register(req.getEmail(), req.getPassword());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/profile")
    public ResponseEntity<UserOutputDto> getProfile(Authentication auth) {
        String email = auth.getName();
        UserOutputDto dto = userService.findByEmail(email);
        return ResponseEntity.ok(dto);
    }
}