// src/main/java/com/example_jelle/backenddico/service/UserService.java
package com.example_jelle.backenddico.service;

import com.example_jelle.backenddico.dto.RegisterRequest; // <-- TOEGEVOEGD
import com.example_jelle.backenddico.dto.UserOutputDto;

public interface UserService {
    /**
     * Registreer een nieuwe gebruiker met de gegevens uit het DTO.
     * @param req Het DTO met alle registratie-informatie. // <-- AANGEPAST
     */
    void register(RegisterRequest req); // <-- AANGEPAST van (String, String) naar (RegisterRequest)

    /**
     * Haal profielgegevens op voor de gegeven e-mail.
     */
    UserOutputDto findByEmail(String email);
}