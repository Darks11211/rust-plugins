package com.sena.usuarioservice.model;
 
import jakarta.persistence.*;
 
@Entity
@Table(name = "usuario")
public class Usuario {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    @Column(nullable = false, length = 100)
    private String nombre;
 
    @Column(nullable = false, unique = true, length = 150)
    private String correo;
 
    @Column(nullable = false)
    private String contrasena;
 
    @Column(nullable = false, length = 60)
    private String pais;
 
    // Valores esperados: "COMPRADOR", "VENDEDOR", "ADMIN"
    @Column(nullable = false, length = 20)
    private String rol;
 
    public Usuario() {
    }
 
    public Usuario(String nombre, String correo, String contrasena, String pais, String rol) {
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.pais = pais;
        this.rol = rol;
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
 
    public String getCorreo() {
        return correo;
    }
 
    public void setCorreo(String correo) {
        this.correo = correo;
    }
 
    public String getContrasena() {
        return contrasena;
    }
 
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
 
    public String getPais() {
        return pais;
    }
 
    public void setPais(String pais) {
        this.pais = pais;
    }
 
    public String getRol() {
        return rol;
    }
 
    public void setRol(String rol) {
        this.rol = rol;
    }
}