package com.sena.ordenservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

// @EnableFeignClients permite que este microservicio llame por HTTP
// a plugin-service (por ejemplo, para consultar el precio de un plugin
// al momento de generar la orden).

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class OrdenServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrdenServiceApplication.class, args);
    }
}
