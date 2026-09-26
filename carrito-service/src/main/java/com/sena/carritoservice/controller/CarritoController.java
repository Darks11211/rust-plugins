package com.sena.carritoservice.controller;

import com.sena.carritoservice.dto.ItemCarritoRequest;
import com.sena.carritoservice.model.ItemCarrito;
import com.sena.carritoservice.service.CarritoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/carrito")
public class CarritoController {

    private final CarritoService carritoService;

    // Inyección por constructor
    public CarritoController(CarritoService carritoService) {
        this.carritoService = carritoService;
    }

    // GET /api/carrito/{idUsuario} -> ver el carrito de un usuario
    @GetMapping("/{idUsuario}")
    public ResponseEntity<List<ItemCarrito>> verCarrito(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(carritoService.verCarrito(idUsuario));
    }

    // POST /api/carrito -> agregar un plugin al carrito
    @PostMapping
    public ResponseEntity<ItemCarrito> agregarItem(@Valid @RequestBody ItemCarritoRequest request) {
        ItemCarrito item = carritoService.agregarItem(
                request.getIdUsuario(),
                request.getIdPlugin(),
                request.getCantidad()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(item);
    }

    // DELETE /api/carrito/item/{idItemCarrito} -> quitar un ítem del carrito
    @DeleteMapping("/item/{idItemCarrito}")
    public ResponseEntity<?> quitarItem(@PathVariable Long idItemCarrito) {
        try {
            carritoService.quitarItem(idItemCarrito);
            Map<String, String> respuesta = new HashMap<>();
            respuesta.put("mensaje", "Ítem eliminado del carrito");
            return ResponseEntity.ok(respuesta);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    // DELETE /api/carrito/{idUsuario} -> vaciar el carrito completo
    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<?> vaciarCarrito(@PathVariable Long idUsuario) {
        carritoService.vaciarCarrito(idUsuario);
        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Carrito vaciado exitosamente");
        return ResponseEntity.ok(respuesta);
    }
}