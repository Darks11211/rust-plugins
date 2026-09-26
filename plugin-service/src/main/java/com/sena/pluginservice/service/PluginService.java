package com.sena.pluginservice.service;

import com.sena.pluginservice.model.Plugin;
import com.sena.pluginservice.repository.PluginRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PluginService {

    private final PluginRepository pluginRepository;

    // Inyección por constructor
    public PluginService(PluginRepository pluginRepository) {
        this.pluginRepository = pluginRepository;
    }

    // Publica un plugin nuevo
    public Plugin publicar(Long idVendedor, String nombre, String descripcion,
                            String version, BigDecimal precio, String archivo) {
        Plugin nuevoPlugin = new Plugin(idVendedor, nombre, descripcion, version, precio, archivo);
        return pluginRepository.save(nuevoPlugin);
    }

    // Lista el catálogo completo (todos los plugins disponibles)
    public List<Plugin> listarTodos() {
        return pluginRepository.findAll();
    }

    // Busca un plugin por su id (lo usará orden-service para consultar precios)
    public Plugin buscarPorId(Long id) {
        return pluginRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plugin no encontrado con id: " + id));
    }

    // Lista los plugins publicados por un vendedor específico
    public List<Plugin> listarPorVendedor(Long idVendedor) {
        return pluginRepository.findByIdVendedor(idVendedor);
    }

    // Busca plugins por coincidencia de nombre (para la barra de búsqueda)
    public List<Plugin> buscarPorNombre(String nombre) {
        return pluginRepository.findByNombreContainingIgnoreCase(nombre);
    }

    // Elimina un plugin (solo debería poder hacerlo su propio vendedor,
    // esa validación se hace en el controller comparando idVendedor)
    public void eliminar(Long id) {
        if (!pluginRepository.existsById(id)) {
            throw new RuntimeException("Plugin no encontrado con id: " + id);
        }
        pluginRepository.deleteById(id);
    }
}