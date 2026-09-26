package com.sena.ordenservice.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "item_orden")
public class ItemOrden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación @ManyToOne normal: Orden vive en este mismo microservicio.
    @ManyToOne
    @JoinColumn(name = "id_orden", nullable = false)
    private Orden orden;

    // Solo el id (Long): Plugin vive en otro microservicio.
    @Column(nullable = false)
    private Long idPlugin;

    // El precio EN EL MOMENTO DE LA COMPRA. Aunque el vendedor cambie
    // el precio del plugin después, esta orden conserva el precio original.
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitario;

    public ItemOrden() {
    }

    public ItemOrden(Orden orden, Long idPlugin, BigDecimal precioUnitario) {
        this.orden = orden;
        this.idPlugin = idPlugin;
        this.precioUnitario = precioUnitario;
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Orden getOrden() {
        return orden;
    }

    public void setOrden(Orden orden) {
        this.orden = orden;
    }

    public Long getIdPlugin() {
        return idPlugin;
    }

    public void setIdPlugin(Long idPlugin) {
        this.idPlugin = idPlugin;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }
}