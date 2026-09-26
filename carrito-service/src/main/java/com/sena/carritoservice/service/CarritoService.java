package com.sena.carritoservice.service;

import com.sena.carritoservice.model.Carrito;
import com.sena.carritoservice.model.ItemCarrito;
import com.sena.carritoservice.repository.CarritoRepository;
import com.sena.carritoservice.repository.ItemCarritoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarritoService {

    private final CarritoRepository carritoRepository;
    private final ItemCarritoRepository itemCarritoRepository;

    // Inyección por constructor
    public CarritoService(CarritoRepository carritoRepository, ItemCarritoRepository itemCarritoRepository) {
        this.carritoRepository = carritoRepository;
        this.itemCarritoRepository = itemCarritoRepository;
    }

    // Obtiene el carrito de un usuario. Si todavía no tiene uno, lo crea
    // automáticamente (esto simplifica: el usuario nunca tiene que "crear"
    // su carrito a mano, se genera solo la primera vez que lo necesita).
    public Carrito obtenerOCrearCarrito(Long idUsuario) {
        return carritoRepository.findByIdUsuario(idUsuario)
                .orElseGet(() -> carritoRepository.save(new Carrito(idUsuario)));
    }

    // Agrega un plugin al carrito. Si ya estaba agregado, solo suma la cantidad.
    public ItemCarrito agregarItem(Long idUsuario, Long idPlugin, Integer cantidad) {
        Carrito carrito = obtenerOCrearCarrito(idUsuario);

        return itemCarritoRepository.findByCarritoAndIdPlugin(carrito, idPlugin)
                .map(item -> {
                    item.setCantidad(item.getCantidad() + cantidad);
                    return itemCarritoRepository.save(item);
                })
                .orElseGet(() -> itemCarritoRepository.save(new ItemCarrito(carrito, idPlugin, cantidad)));
    }

    // Lista todos los ítems del carrito de un usuario
    public List<ItemCarrito> verCarrito(Long idUsuario) {
        Carrito carrito = obtenerOCrearCarrito(idUsuario);
        return itemCarritoRepository.findByCarrito(carrito);
    }

    // Quita un ítem específico del carrito
    public void quitarItem(Long idItemCarrito) {
        if (!itemCarritoRepository.existsById(idItemCarrito)) {
            throw new RuntimeException("Ítem de carrito no encontrado con id: " + idItemCarrito);
        }
        itemCarritoRepository.deleteById(idItemCarrito);
    }

    // Vacía el carrito completo de un usuario (útil después del checkout)
    public void vaciarCarrito(Long idUsuario) {
        Carrito carrito = obtenerOCrearCarrito(idUsuario);
        List<ItemCarrito> items = itemCarritoRepository.findByCarrito(carrito);
        itemCarritoRepository.deleteAll(items);
    }
}