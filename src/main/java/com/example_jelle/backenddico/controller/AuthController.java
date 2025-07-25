package com.example_jelle.backenddico.controller;

import com.example_jelle.backenddico.exceptions.InvalidTokenException;
import com.example_jelle.backenddico.model.User;
import com.example_jelle.backenddico.payload.request.LoginRequest;
import com.example_jelle.backenddico.payload.request.RegisterRequest;
import com.example_jelle.backenddico.payload.request.VerifyEmailRequest;
import com.example_jelle.backenddico.payload.response.JwtResponse;
import com.example_jelle.backenddico.payload.response.MessageResponse;
import com.example_jelle.backenddico.security.CustomUserDetails; // Importeer CustomUserDetails
import com.example_jelle.backenddico.security.JwtUtil;
import com.example_jelle.backenddico.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    // We hebben de UserDetailsService en UserRepository hier niet meer nodig!
    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );

            // OPTIMALISATIE: Haal de volledige user direct uit de principal
            CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
            User user = userDetails.getUser();

            if (!userDetails.isEnabled()) {
                return ResponseEntity.status(403).body(new MessageResponse("Error: Account is not verified. Please check your console for the code."));
            }

            final String token = jwtUtil.generateToken(userDetails);

            // We hebben de 'user' al, dus geen extra database query nodig!
            return ResponseEntity.ok(new JwtResponse(
                    token,
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getRole()
            ));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body(new MessageResponse("Error: Invalid email or password"));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        userService.register(registerRequest);
        return ResponseEntity.ok(new MessageResponse("Registration successful! Please check your console for the verification code."));
    }

    @PostMapping("/verify-email")
    public ResponseEntity<?> verifyEmail(@Valid @RequestBody VerifyEmailRequest verifyRequest) {
        try {
            // Deze methode geeft de volledige, bijgewerkte User terug
            User user = userService.verifyUser(verifyRequest.getEmail(), verifyRequest.getToken());

            // OPTIMALISATIE: Maak de UserDetails direct van de 'user' die we al hebben
            UserDetails userDetails = new CustomUserDetails(user);
            final String jwt = jwtUtil.generateToken(userDetails);

            return ResponseEntity.ok(new JwtResponse(
                    jwt,
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getRole()
            ));
        } catch (InvalidTokenException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
}