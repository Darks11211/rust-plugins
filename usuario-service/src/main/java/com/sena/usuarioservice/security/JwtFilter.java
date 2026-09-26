package com.sena.usuarioservice.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    // Inyección por constructor
    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String headerAutorizacion = request.getHeader("Authorization");

        // El token siempre viene como: "Bearer <token>"
        if (headerAutorizacion != null && headerAutorizacion.startsWith("Bearer ")) {
            String token = headerAutorizacion.substring(7);

            if (jwtUtil.esTokenValido(token)) {
                String correo = jwtUtil.extraerCorreo(token);

                // Le dice a Spring Security: "esta petición ya está autenticada,
                // pertenece al usuario con este correo"
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(correo, null, Collections.emptyList());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // Deja continuar la petición hacia el controller correspondiente
        filterChain.doFilter(request, response);
    }
}