package com.sena.eurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

// TODO: este microservicio es el "directorio telefónico": aquí se registran
// todos los demás (usuario-service, plugin-service, etc.) para que el
// gateway-service sepa dónde encontrarlos.

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}
