package com.jelle.dico.payload.response;

import java.util.Set;

public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String email;
    private Set<String> flags; // Gebruik een Set<String> voor de flags

    public JwtResponse(String accessToken, Long id, String username, String email, Set<String> flags) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.flags = flags;
    }

    // Getters en Setters...
}