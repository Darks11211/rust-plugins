package com.sena.carritoservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

// Este DTO define exactamente qué datos debe traer el JSON
// cuando alguien agrega un plugin a su carrito.
public class ItemCarritoRequest {

    @NotNull(message = "El id del usuario es obligatorio")
    private Long idUsuario;

    @NotNull(message = "El id del plugin es obligatorio")
    private Long idPlugin;

    @NotNull(message = "La cantidad es obligatoria")
    @Positive(message = "La cantidad debe ser mayor a cero")
    private Integer cantidad;

    public ItemCarritoRequest() {
    }

    // Getters y Setters

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdPlugin() {
        return idPlugin;
    }

    public void setIdPlugin(Long idPlugin) {
        this.idPlugin = idPlugin;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}