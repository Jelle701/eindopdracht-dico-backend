// src/main/java/com/example_jelle/backenddico/model/User.java
package com.example_jelle.backenddico.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "users") // Zorgt ervoor dat de tabel 'users' heet
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Deze velden zijn verplicht bij de registratie
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private LocalDate dob;

    // --- DE OPLOSSING ---
    // De 'role' wordt door de backend ingesteld, maar we maken hem voor de zekerheid nullable.
    // Dit voorkomt crashes als de logica ergens faalt.
    @Column(nullable = true)
    private String role;

    // Lege constructor voor JPA
    public User() {}

    // Getters
    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public LocalDate getDob() { return dob; }
    public String getRole() { return role; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setDob(LocalDate dob) { this.dob = dob; }
    public void setRole(String role) { this.role = role; }
}