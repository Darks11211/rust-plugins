package com.sena.ordenservice.controller;

import com.sena.ordenservice.dto.CheckoutRequest;
import com.sena.ordenservice.model.ItemOrden;
import com.sena.ordenservice.model.Orden;
import com.sena.ordenservice.service.OrdenService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ordenes")
public class OrdenController {

    private final OrdenService ordenService;

    // Inyección por constructor
    public OrdenController(OrdenService ordenService) {
        this.ordenService = ordenService;
    }

    // POST /api/ordenes/checkout -> genera una orden a partir de los ítems
    // comprados. Aquí es donde orden-service le pregunta a plugin-service
    // (vía Feign) el precio real de cada plugin.
    @PostMapping("/checkout")
    public ResponseEntity<?> checkout(@Valid @RequestBody CheckoutRequest request) {
        try {
            Orden orden = ordenService.crearOrden(request.getIdUsuario(), request.getItems());
            return ResponseEntity.status(HttpStatus.CREATED).body(orden);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    // GET /api/ordenes/usuario/{idUsuario} -> historial de compras de un usuario
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Orden>> historial(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(ordenService.historialPorUsuario(idUsuario));
    }

    // GET /api/ordenes/{id} -> detalle de una orden específica
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            Orden orden = ordenService.buscarPorId(id);
            return ResponseEntity.ok(orden);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    // GET /api/ordenes/{id}/items -> lista los plugins que componen una orden
    @GetMapping("/{id}/items")
    public ResponseEntity<?> verItems(@PathVariable Long id) {
        try {
            Orden orden = ordenService.buscarPorId(id);
            List<ItemOrden> items = ordenService.verItemsDeOrden(orden);
            return ResponseEntity.ok(items);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    // PUT /api/ordenes/{id}/estado -> cambia el estado de una orden
    // (lo usará pago-service cuando el pago se confirme)
    @PutMapping("/{id}/estado")
    public ResponseEntity<?> actualizarEstado(@PathVariable Long id, @RequestParam String estado) {
        try {
            Orden orden = ordenService.actualizarEstado(id, estado);
            return ResponseEntity.ok(orden);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
}