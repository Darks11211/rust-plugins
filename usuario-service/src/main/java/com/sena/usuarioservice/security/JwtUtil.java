package com.sena.usuarioservice.security;
 
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;
 
import javax.crypto.SecretKey;
import java.util.Date;
 
@Component
public class JwtUtil {
 
    // Llave secreta con la que se firman y validan los tokens.
    // IMPORTANTE: en un proyecto real esto NO se deja escrito en el código,
    // se guarda en application.properties o variables de entorno.
    // Para el proyecto del SENA, dejarlo aquí es aceptable y más simple de explicar.
    private final SecretKey clave = Jwts.SIG.HS256.key().build();
 
    // El token expira en 24 horas (en milisegundos)
    private final long tiempoExpiracion = 1000 * 60 * 60 * 24;
 
    // Genera un token JWT que "identifica" al usuario por su correo
    public String generarToken(String correo) {
        Date ahora = new Date();
        Date expiracion = new Date(ahora.getTime() + tiempoExpiracion);
 
        return Jwts.builder()
                .subject(correo)
                .issuedAt(ahora)
                .expiration(expiracion)
                .signWith(clave)
                .compact();
    }
 
    // Extrae el correo guardado dentro de un token válido
    public String extraerCorreo(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(clave)
                .build()
                .parseSignedClaims(token)
                .getPayload();
 
        return claims.getSubject();
    }
 
    // Verifica si el token es válido (firma correcta y no ha expirado)
    public boolean esTokenValido(String token) {
        try {
            Jwts.parser()
                    .verifyWith(clave)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}