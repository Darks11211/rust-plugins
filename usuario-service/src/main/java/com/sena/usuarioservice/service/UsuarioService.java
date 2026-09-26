package com.sena.usuarioservice.service;

import com.sena.usuarioservice.model.Usuario;
import com.sena.usuarioservice.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario registrar(String nombre, String correo, String contrasena, String pais, String rol) {
        if (usuarioRepository.existsByCorreo(correo)) {
            throw new RuntimeException("Ya existe un usuario registrado con ese correo.");
        }

        String contrasenaEncriptada = passwordEncoder.encode(contrasena);
        Usuario nuevoUsuario = new Usuario(nombre, correo, contrasenaEncriptada, pais, rol);

        return usuarioRepository.save(nuevoUsuario);
    }

    public Usuario buscarPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado."));
    }

    // NUEVO: busca un usuario por su id. Lo usará el endpoint GET /api/usuarios/{id},
    // que a su vez consulta pago-service (vía Feign) para armar la factura.
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
    }

    public boolean validarContrasena(String contrasenaPlana, String contrasenaEncriptada) {
        return passwordEncoder.matches(contrasenaPlana, contrasenaEncriptada);
    }
}