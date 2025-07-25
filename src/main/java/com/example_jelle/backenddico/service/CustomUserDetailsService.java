package com.example_jelle.backenddico.service;

import com.example_jelle.backenddico.model.User;
import com.example_jelle.backenddico.repository.UserRepository;
// FIX: Importeer onze nieuwe CustomUserDetails
import com.example_jelle.backenddico.security.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

        // FIX: Geef onze custom UserDetails implementatie terug
        return new CustomUserDetails(user);
    }
}