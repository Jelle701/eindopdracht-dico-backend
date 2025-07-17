package com.example_jelle.backenddico.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(withDefaults()) // Activeer de CORS-configuratie van WebConfig
                .csrf(AbstractHttpConfigurer::disable) // CSRF is niet nodig voor stateless APIs
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/users/register").permitAll() // Maak registratie publiek
                        // .requestMatchers("/admin/**").hasRole("ADMIN") // Voorbeeld voor beveiligde routes
                        .anyRequest().authenticated() // Beveilig alle andere requests
                );
        return http.build();
    }
}