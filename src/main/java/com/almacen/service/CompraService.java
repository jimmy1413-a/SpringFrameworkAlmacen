package com.almacen.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.almacen.entity.Articulo;
import com.almacen.entity.Compra;
import com.almacen.entity.Compra_articulo;
import com.almacen.entity.Proveedor;
import com.almacen.repository.ArticuloRepository;
import com.almacen.repository.CompraRepository;
import com.almacen.repository.Compra_articuloRepository;
import com.almacen.repository.ProveedorRepository;

@Service
public class CompraService {

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private Compra_articuloRepository compraArtRepository;

    @Autowired
    private ArticuloRepository artRepository;

    @Autowired
    private ProveedorRepository proveedorRepository;

    // Guardar nueva compra
    public Compra guardar(Compra request) {
        // Validar proveedor
        Proveedor proveedor = proveedorRepository.findById(request.getProveedor().getId())
                .orElseThrow(() -> new RuntimeException(
                        "Proveedor no encontrado con ID: " + request.getProveedor().getId()));

        request.setProveedor(proveedor);
        request.setFecha(LocalDateTime.now());

        // Validar y ajustar stock
        for (Compra_articulo detalle : request.getDetalles()) {
            Articulo articulo = artRepository.findById(detalle.getArticulo().getId())
                    .orElseThrow(() -> new RuntimeException(
                            "Artículo no encontrado con ID: " + detalle.getArticulo().getId()));

            detalle.setArticulo(articulo);
            detalle.setCompra(request);

            // Sumar cantidad al stock
            articulo.setCantidad(articulo.getCantidad() + detalle.getCantidad());
            artRepository.save(articulo);
        }

        return compraRepository.save(request);
    }

    // Listar todas las compras
    public List<Compra> listar() {
        return compraRepository.findAll();
    }

    // Obtener compra por ID
    public Compra get(Integer id) {
        return compraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compra no encontrada con ID: " + id));
    }

    // Actualizar compra existente
    public Compra actualizar(Integer id, Compra request) {
        Compra compraExiste = compraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compra no encontrada con ID: " + id));

        Proveedor proveedor = proveedorRepository.findById(request.getProveedor().getId())
                .orElseThrow(() -> new RuntimeException(
                        "Proveedor no encontrado con ID: " + request.getProveedor().getId()));

        compraExiste.setProveedor(proveedor);
        compraExiste.setFecha(LocalDateTime.now());
        compraExiste.setValor(request.getValor());

        // Limpiar detalles antiguos
        if (compraExiste.getDetalles() != null) {
            compraExiste.getDetalles().clear();
        } else {
            compraExiste.setDetalles(new ArrayList<>());
        }

        // Ajustar detalles y stock
        for (Compra_articulo detalle : request.getDetalles()) {
            Articulo articulo = artRepository.findById(detalle.getArticulo().getId())
                    .orElseThrow(() -> new RuntimeException(
                            "Artículo no encontrado con ID: " + detalle.getArticulo().getId()));

            detalle.setArticulo(articulo);
            detalle.setCompra(compraExiste);

            compraExiste.getDetalles().add(detalle);

            // Sumar cantidad al stock
            articulo.setCantidad(articulo.getCantidad() + detalle.getCantidad());
            artRepository.save(articulo);
        }

        return compraRepository.save(compraExiste);
    }

    // Eliminar compra por ID
    public boolean eliminar(Integer id) {
        if (compraRepository.existsById(id)) {
            compraRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
