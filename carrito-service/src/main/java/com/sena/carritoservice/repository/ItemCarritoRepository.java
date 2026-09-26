package com.sena.carritoservice.repository;

import com.sena.carritoservice.model.Carrito;
import com.sena.carritoservice.model.ItemCarrito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemCarritoRepository extends JpaRepository<ItemCarrito, Long> {

    // Todos los ítems (plugins) dentro de un carrito específico
    List<ItemCarrito> findByCarrito(Carrito carrito);

    // Para saber si un plugin ya está agregado en ese carrito (evitar duplicados)
    Optional<ItemCarrito> findByCarritoAndIdPlugin(Carrito carrito, Long idPlugin);
}