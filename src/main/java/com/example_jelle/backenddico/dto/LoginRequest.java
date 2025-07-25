package com.example_jelle.backenddico.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank(message = "Email is required.")
    @Email(message = "Please provide a valid email address.")
    private String email; // AANGEPAST: van username naar email

    @NotBlank(message = "Password is required.")
    private String password;

    // Getters en Setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}