package com.sena.pagoservice.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "pago")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Solo el id (Long): Orden vive en otro microservicio (orden-service).
    @Column(nullable = false, unique = true)
    private Long idOrden;

    // Valores esperados: "TARJETA", "PSE", "PAYPAL", etc.
    @Column(nullable = false, length = 30)
    private String metodo;

    // Código de moneda, ej: "COP", "USD", "EUR"
    @Column(nullable = false, length = 10)
    private String moneda;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal montoConvertido;

    public Pago() {
    }

    public Pago(Long idOrden, String metodo, String moneda, BigDecimal montoConvertido) {
        this.idOrden = idOrden;
        this.metodo = metodo;
        this.moneda = moneda;
        this.montoConvertido = montoConvertido;
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(Long idOrden) {
        this.idOrden = idOrden;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public BigDecimal getMontoConvertido() {
        return montoConvertido;
    }

    public void setMontoConvertido(BigDecimal montoConvertido) {
        this.montoConvertido = montoConvertido;
    }
}