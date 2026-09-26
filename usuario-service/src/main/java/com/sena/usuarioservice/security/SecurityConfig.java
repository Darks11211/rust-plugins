package com.sena.usuarioservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    // Inyección por constructor
    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    // Bean = un "objeto administrado por Spring" que cualquier otra clase
    // puede pedir prestado. BCrypt es el algoritmo estándar para encriptar
    // contraseñas: nunca se guarda la contraseña real, solo su versión encriptada.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Desactivamos CSRF porque es una API REST sin formularios HTML tradicionales
            .csrf(csrf -> csrf.disable())

            // Sin sesiones guardadas en el servidor: cada petición se valida
            // con su propio token JWT
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            .authorizeHttpRequests(auth -> auth
                // Registro y login son públicos: cualquiera puede acceder sin token
                .requestMatchers("/api/auth/**").permitAll()
                // El resto de rutas de este microservicio sí requerirán autenticación
                .anyRequest().authenticated()
            )

            // Insertamos nuestro JwtFilter para que revise el token
            // ANTES de que Spring intente su propio proceso de login
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}