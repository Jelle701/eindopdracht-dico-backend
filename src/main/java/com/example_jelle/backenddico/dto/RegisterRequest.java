package com.example_jelle.backenddico.dto;

// V V V GEWIJZIGD NAAR JAVAX V V V
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RegisterRequest {

    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotNull
    private Boolean onboardingCompleted;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Size(min = 2)
    private String role;

    // Getters en setters...
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Boolean getOnboardingCompleted() { return onboardingCompleted; }
    public void setOnboardingCompleted(Boolean onboardingCompleted) { this.onboardingCompleted = onboardingCompleted; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
