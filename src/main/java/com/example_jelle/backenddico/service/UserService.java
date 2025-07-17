package com.example_jelle.backenddico.service;

import com.example_jelle.backenddico.dto.UserOutputDto;

public interface UserService {
    /**
     * Registreer een nieuwe gebruiker met e-mail en wachtwoord
     */
    void register(String email, String password);

    /**
     * Haal profielgegevens op voor de gegeven e-mail
     */
    UserOutputDto findByEmail(String email);
}