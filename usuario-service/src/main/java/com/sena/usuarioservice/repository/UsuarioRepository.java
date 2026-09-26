package com.sena.usuarioservice.repository;
 
import com.sena.usuarioservice.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
 
import java.util.Optional;
 
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
 
    // Para el login: buscar usuario por correo
    Optional<Usuario> findByCorreo(String correo);
 
    // Útil para validar que el correo no esté ya registrado
    boolean existsByCorreo(String correo);
}