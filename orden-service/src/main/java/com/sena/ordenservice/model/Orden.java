package com.sena.ordenservice.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "orden")
public class Orden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Solo el id (Long), NO @ManyToOne: Usuario vive en otro microservicio.
    @Column(nullable = false)
    private Long idUsuario;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    // Valores esperados: "PENDIENTE", "PAGADA", "CANCELADA"
    @Column(nullable = false, length = 20)
    private String estado;

    public Orden() {
    }

    public Orden(Long idUsuario, LocalDate fecha, BigDecimal total, String estado) {
        this.idUsuario = idUsuario;
        this.fecha = fecha;
        this.total = total;
        this.estado = estado;
    }

    // Getters y Setters

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

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
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