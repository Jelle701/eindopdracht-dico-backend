// src/main/java/com/example_jelle/backenddico/dto/RegisterRequest.java
package com.example_jelle.backenddico.dto;

import java.time.LocalDate;

public class RegisterRequest {
    // --- TOEGEVOEGD ---
    private String firstName;
    private String lastName;
    private LocalDate dob;
    // --- EINDE ---
    private String email;
    private String password;

    public RegisterRequest() {}

    // --- GETTERS EN SETTERS VOOR ALLE VELDEN ---
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