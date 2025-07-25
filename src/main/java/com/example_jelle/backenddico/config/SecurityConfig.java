package com.example_jelle.backenddico.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Dit veld is nodig om de JWT filter te kunnen injecteren.
    private final JwtRequestFilter jwtRequestFilter;

    // De constructor voor dependency injection.
    public SecurityConfig(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    // Bean voor het hashen van wachtwoorden.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Bean voor het beheren van de authenticatie.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    // Bean voor het configureren van de security filter chain.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF voor stateless APIs
                .cors(withDefaults()) // Activeer CORS en gebruik de 'corsConfigurationSource' bean
                .authorizeHttpRequests(auth -> auth
                        // Publieke endpoints voor authenticatie
                        .requestMatchers("/api/auth/**").permitAll()
                        // Alle andere requests moeten geauthenticeerd zijn
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    // Bean voor de CORS-configuratie.
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // De origin van de frontend applicatie
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        // De HTTP-methodes die je wilt toestaan
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        // De headers die je wilt toestaan
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        // Sta credentials toe (belangrijk voor het versturen van auth headers)
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Pas deze configuratie toe op alle paden in de applicatie
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}