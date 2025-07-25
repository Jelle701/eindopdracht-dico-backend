package com.example_jelle.backenddico.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;

    private String firstName;
    private String lastName;
    private LocalDate dob;

    // Dit veld wordt gebruikt door Spring Security's UserDetails contract.
    @Column(nullable = false)
    private boolean enabled = false;

    // --- NIEUWE VELDEN VOLGENS SPECIFICATIES ---

    @Embedded
    private UserFlags flags;

    private String verificationCode;
    private LocalDateTime verificationCodeExpires;

    // --- CONSTRUCTOR ---
    public User() {
        // Initialiseer het flags object om NullPointerExceptions te voorkomen
        this.flags = new UserFlags();
    }

    // --- GETTERS EN SETTERS ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) { this.dob = dob; }

    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    public UserFlags getFlags() { return flags; }
    public void setFlags(UserFlags flags) { this.flags = flags; }

    public String getVerificationCode() { return verificationCode; }
    public void setVerificationCode(String verificationCode) { this.verificationCode = verificationCode; }

    public LocalDateTime getVerificationCodeExpires() { return verificationCodeExpires; }
    public void setVerificationCodeExpires(LocalDateTime verificationCodeExpires) { this.verificationCodeExpires = verificationCodeExpires; }
}