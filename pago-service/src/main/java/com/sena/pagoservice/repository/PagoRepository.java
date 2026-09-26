package com.sena.pagoservice.repository;

import com.sena.pagoservice.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PagoRepository extends JpaRepository<Pago, Long> {

    // Cada orden tiene un único pago asociado
    Optional<Pago> findByIdOrden(Long idOrden);
}