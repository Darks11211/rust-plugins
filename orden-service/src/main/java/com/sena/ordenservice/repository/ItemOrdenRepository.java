package com.sena.ordenservice.repository;

import com.sena.ordenservice.model.ItemOrden;
import com.sena.ordenservice.model.Orden;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemOrdenRepository extends JpaRepository<ItemOrden, Long> {

    // Todos los plugins que quedaron dentro de una orden específica
    List<ItemOrden> findByOrden(Orden orden);
}