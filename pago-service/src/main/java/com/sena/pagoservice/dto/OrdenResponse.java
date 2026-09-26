package com.sena.pagoservice.dto;

import java.math.BigDecimal;

// Representa la respuesta que llega desde orden-service
// cuando pago-service pregunta por una orden específica.
public class OrdenResponse {

    private Long id;
    private Long idUsuario;
    private String fecha;
    private BigDecimal total;
    private String estado;

    public OrdenResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}