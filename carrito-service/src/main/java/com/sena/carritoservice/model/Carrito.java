package com.sena.carritoservice.model;

import jakarta.persistence.*;

@Entity
@Table(name = "carrito")
public class Carrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Igual que idVendedor en Plugin: aquí NO hay @ManyToOne hacia Usuario,
    // porque Usuario vive en otro microservicio con su propia base de datos.
    // Solo guardamos el id del usuario como un número simple.
    @Column(nullable = false, unique = true)
    private Long idUsuario;

    public Carrito() {
    }

    public Carrito(Long idUsuario) {
        this.idUsuario = idUsuario;
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
}