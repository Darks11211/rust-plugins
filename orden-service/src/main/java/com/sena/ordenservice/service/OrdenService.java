package com.sena.ordenservice.service;

import com.sena.ordenservice.client.PluginClient;
import com.sena.ordenservice.dto.PluginResponse;
import com.sena.ordenservice.model.ItemOrden;
import com.sena.ordenservice.model.Orden;
import com.sena.ordenservice.repository.ItemOrdenRepository;
import com.sena.ordenservice.repository.OrdenRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class OrdenService {

    private final OrdenRepository ordenRepository;
    private final ItemOrdenRepository itemOrdenRepository;
    private final PluginClient pluginClient;

    // Inyección por constructor
    public OrdenService(OrdenRepository ordenRepository, ItemOrdenRepository itemOrdenRepository, PluginClient pluginClient) {
        this.ordenRepository = ordenRepository;
        this.itemOrdenRepository = itemOrdenRepository;
        this.pluginClient = pluginClient;
    }

    // Crea una orden nueva a partir de los ítems del carrito.
    // 'itemsCarrito' es un mapa simple: idPlugin -> cantidad.
    // Por cada plugin, le PREGUNTAMOS A plugin-service (vía Feign) cuál
    // es su precio ACTUAL, y ese precio queda "congelado" en el ItemOrden.
    public Orden crearOrden(Long idUsuario, Map<Long, Integer> itemsCarrito) {

        BigDecimal total = BigDecimal.ZERO;

        // Primero creamos la orden con total en cero (se actualiza después)
        Orden orden = new Orden(idUsuario, LocalDate.now(), BigDecimal.ZERO, "PENDIENTE");
        orden = ordenRepository.save(orden);

        for (Map.Entry<Long, Integer> entrada : itemsCarrito.entrySet()) {
            Long idPlugin = entrada.getKey();
            Integer cantidad = entrada.getValue();

            // Llamada real por HTTP hacia plugin-service (vía Feign)
            PluginResponse plugin = pluginClient.obtenerPlugin(idPlugin);

            BigDecimal precioUnitario = plugin.getPrecio();
            BigDecimal subtotal = precioUnitario.multiply(BigDecimal.valueOf(cantidad));
            total = total.add(subtotal);

            ItemOrden item = new ItemOrden(orden, idPlugin, precioUnitario);
            itemOrdenRepository.save(item);
        }

        orden.setTotal(total);
        return ordenRepository.save(orden);
    }

    // Historial de compras de un usuario
    public List<Orden> historialPorUsuario(Long idUsuario) {
        return ordenRepository.findByIdUsuario(idUsuario);
    }

    // Busca una orden por id
    public Orden buscarPorId(Long id) {
        return ordenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada con id: " + id));
    }

    // Lista los ítems (plugins) que componen una orden
    public List<ItemOrden> verItemsDeOrden(Orden orden) {
        return itemOrdenRepository.findByOrden(orden);
    }

    // Cambia el estado de una orden (lo usará pago-service cuando el pago se confirme)
    public Orden actualizarEstado(Long idOrden, String nuevoEstado) {
        Orden orden = buscarPorId(idOrden);
        orden.setEstado(nuevoEstado);
        return ordenRepository.save(orden);
    }
}