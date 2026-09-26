package com.sena.carritoservice.repository;

import com.sena.carritoservice.model.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarritoRepository extends JpaRepository<Carrito, Long> {

    // Cada usuario tiene un único carrito: lo buscamos por su id de usuario
    Optional<Carrito> findByIdUsuario(Long idUsuario);
}