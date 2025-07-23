package com.example_jelle.backenddico.dto; // Aangepast naar de juiste package

// V V V GEWIJZIGD NAAR JAKARTA V V V
import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    // Getters en Setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
