package com.sena.pluginservice.controller;

import com.sena.pluginservice.dto.PluginRequest;
import com.sena.pluginservice.model.Plugin;
import com.sena.pluginservice.service.PluginService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/plugins")
public class PluginController {

    private final PluginService pluginService;

    // Inyección por constructor
    public PluginController(PluginService pluginService) {
        this.pluginService = pluginService;
    }

    // GET /api/plugins -> lista el catálogo completo
    // GET /api/plugins?nombre=xyz -> busca por nombre
    @GetMapping
    public ResponseEntity<List<Plugin>> listar(@RequestParam(required = false) String nombre) {
        if (nombre != null && !nombre.isBlank()) {
            return ResponseEntity.ok(pluginService.buscarPorNombre(nombre));
        }
        return ResponseEntity.ok(pluginService.listarTodos());
    }

    // GET /api/plugins/{id} -> detalle de un plugin específico
    // Este endpoint es el que orden-service va a consultar (vía Feign)
    // para saber el precio real de un plugin al momento de comprar.
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            Plugin plugin = pluginService.buscarPorId(id);
            return ResponseEntity.ok(plugin);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    // GET /api/plugins/vendedor/{idVendedor} -> plugins publicados por un vendedor
    @GetMapping("/vendedor/{idVendedor}")
    public ResponseEntity<List<Plugin>> listarPorVendedor(@PathVariable Long idVendedor) {
        return ResponseEntity.ok(pluginService.listarPorVendedor(idVendedor));
    }

    // POST /api/plugins -> publica un plugin nuevo
    @PostMapping
    public ResponseEntity<?> publicar(@Valid @RequestBody PluginRequest request) {
        Plugin plugin = pluginService.publicar(
                request.getIdVendedor(),
                request.getNombre(),
                request.getDescripcion(),
                request.getVersion(),
                request.getPrecio(),
                request.getArchivo()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(plugin);
    }

    // DELETE /api/plugins/{id} -> elimina un plugin
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            pluginService.eliminar(id);
            Map<String, String> respuesta = new HashMap<>();
            respuesta.put("mensaje", "Plugin eliminado exitosamente");
            return ResponseEntity.ok(respuesta);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
}