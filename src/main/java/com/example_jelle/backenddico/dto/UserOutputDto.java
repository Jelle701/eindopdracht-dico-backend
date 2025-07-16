package com.example_jelle.backenddico.dto;

public class UserOutputDto {
    private Long id;
    private String email;
    private String role;

    public UserOutputDto(Long id, String email, String role) {
        this.id = id;
        this.email = email;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }
}
