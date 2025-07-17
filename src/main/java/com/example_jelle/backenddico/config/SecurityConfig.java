// src/main/java/com/example_jelle/backenddico/config/SecurityConfig.java
package com.example_jelle.backenddico.config;

import com.example_jelle.backenddico.security.JwtConfigurer;
import com.example_jelle.backenddico.security.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final JwtUtil jwtUtil;

    public SecurityConfig(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Stateless JWT: CSRF uit, geen sessies
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Autorisatieregels
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST,
                                "/api/users/register",
                                "/api/users/resend-verification"
                        ).permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                        .requestMatchers("/api/users/profile", "/api/users/devices").authenticated()
                        .anyRequest().permitAll()
                )
                // JWT-filter in de chain (jouw JwtConfigurer)
                .apply(new JwtConfigurer(jwtUtil));

        return http.build();
    }

    /**
     * Exposeer AuthenticationManager zodat AuthController deze kan injecteren.
     */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig
    ) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
