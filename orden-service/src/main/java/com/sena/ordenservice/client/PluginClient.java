package com.sena.ordenservice.client;

import com.sena.ordenservice.dto.PluginResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// @FeignClient(name = "plugin-service") le dice a Spring: "cuando llames
// a los métodos de esta interfaz, en realidad haz una petición HTTP
// hacia el microservicio registrado en Eureka con el nombre plugin-service".
//
// No hace falta escribir la URL completa (http://localhost:8082/...) porque
// Eureka ya sabe en qué dirección/puerto vive plugin-service en este momento.
@FeignClient(name = "plugin-service")
public interface PluginClient {

    // Esto reproduce exactamente el endpoint GET /api/plugins/{id}
    // que ya existe en PluginController. Cuando orden-service llame a este
    // método, por debajo se ejecuta una petición HTTP real hacia plugin-service.
    @GetMapping("/api/plugins/{id}")
    PluginResponse obtenerPlugin(@PathVariable("id") Long id);
}