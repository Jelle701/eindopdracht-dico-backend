// src/main/java/com/example_jelle/backenddico/dto/RegisterRequest.java
package com.example_jelle.backenddico.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class RegisterRequest {
    @NotBlank(message = "Voornaam is verplicht")
    private String firstName;

    @NotBlank(message = "Achternaam is verplicht")
    private String lastName;

    @NotNull(message = "Geboortedatum is verplicht")
    private LocalDate dob;

    @NotBlank(message = "E-mail is verplicht")
    @Email(message = "Ongeldig e-mailadres")
    private String email;

    @NotBlank(message = "Wachtwoord is verplicht")
    @Size(min = 6, message = "Wachtwoord moet minimaal 6 karakters lang zijn")
    private String password;

    // Getters en Setters
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) { this.dob = dob; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}