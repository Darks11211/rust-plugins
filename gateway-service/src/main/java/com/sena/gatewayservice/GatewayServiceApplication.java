package com.sena.gatewayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// TODO: este es el punto de entrada único. El frontend (HTML/JS) le habla
// solo a este microservicio, y él redirige la petición al microservicio
// correspondiente (usuario-service, plugin-service, etc.) según la ruta.

@SpringBootApplication
public class GatewayServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayServiceApplication.class, args);
    }
}
