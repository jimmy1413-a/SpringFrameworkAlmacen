package com.almacen.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.almacen.entity.Articulo;
import com.almacen.entity.Cliente;
import com.almacen.entity.Venta;
import com.almacen.entity.Venta_articulo;
import com.almacen.repository.ArticuloRepository;
import com.almacen.repository.ClienteRepository;
import com.almacen.repository.VentaRepository;
import com.almacen.repository.Venta_articuloRepository;

@Service
@Transactional
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private Venta_articuloRepository ventaArtRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ArticuloRepository artRepository;

    // Guardar una nueva venta
    public Venta guardar(Venta request) {
        // Validar cliente
        Cliente cliente = clienteRepository.findById(request.getCliente().getId())
                .orElseThrow(() -> new RuntimeException(
                        "Cliente no encontrado con ID: " + request.getCliente().getId()));
        request.setCliente(cliente);
        request.setFecha(LocalDateTime.now());

        // Validar stock primero
        for (Venta_articulo detalle : request.getDetalles()) {
            Articulo articulo = artRepository.findById(detalle.getArticulo().getId())
                    .orElseThrow(() -> new RuntimeException(
                            "Artículo no encontrado con ID: " + detalle.getArticulo().getId()));
            if (articulo.getCantidad() < detalle.getCantidad()) {
                throw new RuntimeException(
                        "Stock insuficiente para el artículo con ID: " + articulo.getId());
            }
        }

        // Ajustar stock y asignar detalles
        for (Venta_articulo detalle : request.getDetalles()) {
            Articulo articulo = artRepository.findById(detalle.getArticulo().getId()).get();
            detalle.setArticulo(articulo);
            detalle.setVenta(request);
            articulo.setCantidad(articulo.getCantidad() - detalle.getCantidad());
            artRepository.save(articulo);
        }

        return ventaRepository.save(request);
    }

    // Listar todas las ventas
    public List<Venta> listar() {
        return ventaRepository.findAll();
    }

    // Obtener venta por ID
    public Venta get(Integer id) {
        return ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Venta no encontrada con ID: " + id));
    }

    // Actualizar venta existente
    public Venta actualizar(Integer id, Venta request) {
        Venta ventaExistente = ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Venta no encontrada con ID: " + id));

        // Validar cliente
        Cliente cliente = clienteRepository.findById(request.getCliente().getId())
                .orElseThrow(() -> new RuntimeException(
                        "Cliente no encontrado con ID: " + request.getCliente().getId()));
        ventaExistente.setCliente(cliente);
        ventaExistente.setFecha(LocalDateTime.now());
        ventaExistente.setValor(request.getValor());

        // Limpiar detalles antiguos
        if (ventaExistente.getDetalles() != null) {
            ventaExistente.getDetalles().clear();
        } else {
            ventaExistente.setDetalles(new ArrayList<>());
        }

        // Validar stock de los detalles nuevos
        for (Venta_articulo detalle : request.getDetalles()) {
            Articulo articulo = artRepository.findById(detalle.getArticulo().getId())
                    .orElseThrow(() -> new RuntimeException(
                            "Artículo no encontrado con ID: " + detalle.getArticulo().getId()));
            if (articulo.getCantidad() < detalle.getCantidad()) {
                throw new RuntimeException(
                        "Stock insuficiente para el artículo con ID: " + articulo.getId());
            }
        }

        // Ajustar stock y asignar detalles
        for (Venta_articulo detalle : request.getDetalles()) {
            Articulo articulo = artRepository.findById(detalle.getArticulo().getId())
                    .orElseThrow(() -> new RuntimeException(
                            "Artículo no encontrado con ID: " + detalle.getArticulo().getId()));
            detalle.setArticulo(articulo);
            detalle.setVenta(ventaExistente);
            ventaExistente.getDetalles().add(detalle);
            articulo.setCantidad(articulo.getCantidad() - detalle.getCantidad());
            artRepository.save(articulo);
        }

        return ventaRepository.save(ventaExistente);
    }

    // Eliminar venta por ID
    public boolean eliminar(Integer id) {
        if (ventaRepository.existsById(id)) {
            ventaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
