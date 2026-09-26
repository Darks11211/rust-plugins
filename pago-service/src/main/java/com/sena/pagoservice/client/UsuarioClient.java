package com.sena.pagoservice.client;

import com.sena.pagoservice.dto.UsuarioResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Permite a pago-service consultar el correo del comprador,
// necesario para enviarle la factura.
@FeignClient(name = "usuario-service")
public interface UsuarioClient {

    @GetMapping("/api/usuarios/{id}")
    UsuarioResponse obtenerUsuario(@PathVariable("id") Long id);
}