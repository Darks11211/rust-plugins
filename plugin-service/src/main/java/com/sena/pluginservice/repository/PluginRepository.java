package com.sena.pluginservice.repository;

import com.sena.pluginservice.model.Plugin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PluginRepository extends JpaRepository<Plugin, Long> {

    // Listar todos los plugins que ha publicado un vendedor específico
    List<Plugin> findByIdVendedor(Long idVendedor);

    // Buscar plugins por nombre (para la barra de búsqueda del catálogo)
    List<Plugin> findByNombreContainingIgnoreCase(String nombre);
}