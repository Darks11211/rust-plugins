package com.sena.pagoservice.client;

import com.sena.pagoservice.dto.OrdenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

// Permite a pago-service consultar el detalle de una orden (para la
// factura), y también actualizar su estado a PAGADA una vez se confirme el pago.
@FeignClient(name = "orden-service")
public interface OrdenClient {

    @GetMapping("/api/ordenes/{id}")
    OrdenResponse obtenerOrden(@PathVariable("id") Long id);

    @PutMapping("/api/ordenes/{id}/estado")
    OrdenResponse actualizarEstado(@PathVariable("id") Long id, @RequestParam("estado") String estado);
}