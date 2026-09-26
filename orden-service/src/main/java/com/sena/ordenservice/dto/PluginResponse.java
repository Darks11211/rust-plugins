package com.sena.ordenservice.dto;

import java.math.BigDecimal;

// Este DTO representa la respuesta que llega desde plugin-service
// cuando orden-service le pregunta por un plugin específico.
public class PluginResponse {

    private Long id;
    private String nombre;
    private BigDecimal precio;

    public PluginResponse() {
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }
}