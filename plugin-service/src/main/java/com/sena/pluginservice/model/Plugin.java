package com.sena.pluginservice.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "plugin")
public class Plugin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Aquí NO usamos @ManyToOne hacia Usuario, porque Usuario vive en OTRO
    // microservicio (usuario-service) con su propia base de datos separada.
    // Solo guardamos el id del vendedor como un número simple.
    @Column(nullable = false)
    private Long idVendedor;

    @Column(nullable = false, length = 120)
    private String nombre;

    @Column(length = 1000)
    private String descripcion;

    @Column(nullable = false, length = 20)
    private String version;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    // Ruta o nombre del archivo .cs subido
    @Column(nullable = false, length = 255)
    private String archivo;

    public Plugin() {
    }

    public Plugin(Long idVendedor, String nombre, String descripcion, String version, BigDecimal precio, String archivo) {
        this.idVendedor = idVendedor;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.version = version;
        this.precio = precio;
        this.archivo = archivo;
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(Long idVendedor) {
        this.idVendedor = idVendedor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }
}