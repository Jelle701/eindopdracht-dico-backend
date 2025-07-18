// src/main/java/com/example_jelle/backenddico/dto/UserOutputDto.java
package com.example_jelle.backenddico.dto;

import com.example_jelle.backenddico.model.User;

public class UserOutputDto {
    private Long id;
    private String email;
    // --- TOEGEVOEGD ---
    private String firstName;
    private String lastName;
    private String role;

    public UserOutputDto() {}

    // Constructor aangepast om nieuwe velden te accepteren
    public UserOutputDto(Long id, String email, String firstName, String lastName, String role) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    // `from` methode aangepast om de nieuwe velden te mappen
    public static UserOutputDto from(User user) {
        return new UserOutputDto(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole()
        );
    }

    // --- GETTERS EN SETTERS VOOR ALLE VELDEN ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}