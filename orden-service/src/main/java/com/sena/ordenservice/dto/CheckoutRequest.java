package com.sena.ordenservice.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Map;

// Este DTO define exactamente qué datos debe traer el JSON
// cuando el usuario finaliza su compra (checkout).
//
// 'items' es un mapa: la clave es el idPlugin, el valor es la cantidad.
// Ejemplo en JSON: { "idUsuario": 1, "items": { "1": 2, "3": 1 } }
// significa: 2 unidades del plugin 1, y 1 unidad del plugin 3.
public class CheckoutRequest {

    @NotNull(message = "El id del usuario es obligatorio")
    private Long idUsuario;

    @NotEmpty(message = "Debe incluir al menos un plugin para comprar")
    private Map<Long, Integer> items;

    public CheckoutRequest() {
    }

    // Getters y Setters

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Map<Long, Integer> getItems() {
        return items;
    }

    public void setItems(Map<Long, Integer> items) {
        this.items = items;
    }
}