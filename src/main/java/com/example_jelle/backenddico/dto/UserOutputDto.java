package com.example_jelle.backenddico.dto;

import com.example_jelle.backenddico.model.User;

public class UserOutputDto {
    private Long id;
    private String email;

    // No-arg constructor required by Jackson/JPA
    public UserOutputDto() {}

    // <-- This constructor fixes the "no suitable constructor" error
    public UserOutputDto(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    public static UserOutputDto from(User user) {
        return new UserOutputDto(user.getId(), user.getEmail());
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
