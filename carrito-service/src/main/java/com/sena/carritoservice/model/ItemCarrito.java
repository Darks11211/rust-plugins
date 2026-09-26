package com.sena.carritoservice.model;

import jakarta.persistence.*;

@Entity
@Table(name = "item_carrito")
public class ItemCarrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Esta sí es una relación @ManyToOne normal, porque Carrito
    // vive EN ESTE MISMO microservicio (misma base de datos carrito_db).
    @ManyToOne
    @JoinColumn(name = "id_carrito", nullable = false)
    private Carrito carrito;

    // idPlugin es solo un número, NO una relación @ManyToOne,
    // porque Plugin vive en otro microservicio (plugin_db).
    @Column(nullable = false)
    private Long idPlugin;

    @Column(nullable = false)
    private Integer cantidad;

    public ItemCarrito() {
    }

    public ItemCarrito(Carrito carrito, Long idPlugin, Integer cantidad) {
        this.carrito = carrito;
        this.idPlugin = idPlugin;
        this.cantidad = cantidad;
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Carrito getCarrito() {
        return carrito;
    }

    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
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