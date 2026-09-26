package com.sena.usuarioservice.controller;

import com.sena.usuarioservice.dto.LoginRequest;
import com.sena.usuarioservice.dto.RegistroRequest;
import com.sena.usuarioservice.model.Usuario;
import com.sena.usuarioservice.security.JwtUtil;
import com.sena.usuarioservice.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {

    private final UsuarioService usuarioService;
    private final JwtUtil jwtUtil;

    public AuthController(UsuarioService usuarioService, JwtUtil jwtUtil) {
        this.usuarioService = usuarioService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/api/auth/registro")
    public ResponseEntity<?> registrar(@Valid @RequestBody RegistroRequest request) {
        try {
            Usuario usuario = usuarioService.registrar(
                    request.getNombre(),
                    request.getCorreo(),
                    request.getContrasena(),
                    request.getPais(),
                    "COMPRADOR"
            );

            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("mensaje", "Usuario registrado exitosamente");
            respuesta.put("id", usuario.getId());
            respuesta.put("correo", usuario.getCorreo());

            return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);

        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @PostMapping("/api/auth/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            Usuario usuario = usuarioService.buscarPorCorreo(request.getCorreo());

            boolean contrasenaValida = usuarioService.validarContrasena(
                    request.getContrasena(),
                    usuario.getContrasena()
            );

            if (!contrasenaValida) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Correo o contraseña incorrectos");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
            }

            String token = jwtUtil.generarToken(usuario.getCorreo());

            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("token", token);
            respuesta.put("nombre", usuario.getNombre());
            respuesta.put("correo", usuario.getCorreo());
            respuesta.put("rol", usuario.getRol());

            return ResponseEntity.ok(respuesta);

        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Correo o contraseña incorrectos");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
    }

    // GET /api/usuarios/{id}
    // Endpoint NUEVO: permite que otros microservicios (como pago-service,
    // vía Feign) consulten los datos básicos de un usuario, incluyendo su correo.
    @GetMapping("/api/usuarios/{id}")
    public ResponseEntity<?> obtenerUsuario(@PathVariable Long id) {
        try {
            Usuario usuario = usuarioService.buscarPorId(id);

            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("id", usuario.getId());
            respuesta.put("nombre", usuario.getNombre());
            respuesta.put("correo", usuario.getCorreo());

            return ResponseEntity.ok(respuesta);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
}