package com.sena.ordenservice.repository;

import com.sena.ordenservice.model.Orden;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdenRepository extends JpaRepository<Orden, Long> {

    // Historial de compras de un usuario
    List<Orden> findByIdUsuario(Long idUsuario);
}