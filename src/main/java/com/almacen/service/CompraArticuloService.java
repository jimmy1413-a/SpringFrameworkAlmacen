package com.almacen.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.almacen.entity.Articulo;
import com.almacen.entity.Compra;
import com.almacen.entity.Compra_articulo;
import com.almacen.repository.ArticuloRepository;
import com.almacen.repository.CompraRepository;
import com.almacen.repository.Compra_articuloRepository;


public class CompraArticuloService {

    private  Compra_articuloRepository repository;
    private ArticuloRepository articuloRepository;
    private  CompraRepository compraRepository;

    // Crear una nueva Compra_articulo
    @Transactional
    public Compra_articulo create(Compra_articulo ca) {
        // Validaciones básicas
        if (ca.getCantidad() == null || ca.getCantidad() <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero");
        }
        if (ca.getValor_unitario() == null || ca.getValor_unitario() <= 0) {
            throw new IllegalArgumentException("El valor unitario debe ser mayor a cero");
        }

        // Asignar artículo válido
        if (ca.getArticulo() != null) {
            Integer articuloId = ca.getArticulo().getId();
            Articulo articulo = articuloRepository.findById(articuloId)
                    .orElseThrow(() -> new RuntimeException(
                            "Articulo no encontrado con ID: " + articuloId));
            ca.setArticulo(articulo);

            // Actualizar stock del artículo
            articulo.setCantidad(articulo.getCantidad() + ca.getCantidad());
            articuloRepository.save(articulo);
        }

        // Asignar compra válida
        if (ca.getCompra() != null) {
            Integer compraId = ca.getCompra().getId();
            Compra compra = compraRepository.findById(compraId)
                    .orElseThrow(() -> new RuntimeException(
                            "Compra no encontrada con ID: " + compraId));
            ca.setCompra(compra);
        }

        // Calcular valor total
        ca.setValor(ca.getCantidad() * ca.getValor_unitario());

        return repository.save(ca);
    }

    // Actualizar una Compra_articulo existente
    @Transactional
    public Compra_articulo update(Compra_articulo ca) {
        Compra_articulo existing = repository.findById(ca.getId())
                .orElseThrow(() -> new RuntimeException(
                        "Compra_articulo no encontrado con ID: " + ca.getId()));

        // Actualizar campos
        existing.setCantidad(ca.getCantidad() != null ? ca.getCantidad() : existing.getCantidad());
        existing.setValor_unitario(ca.getValor_unitario() != null ? ca.getValor_unitario() : existing.getValor_unitario());

        // Recalcular valor total
        existing.setValor(existing.getCantidad() * existing.getValor_unitario());

        // Actualizar artículo y stock si es necesario
        if (ca.getArticulo() != null) {
            Integer articuloId = ca.getArticulo().getId();
            Articulo articulo = articuloRepository.findById(articuloId)
                    .orElseThrow(() -> new RuntimeException(
                            "Articulo no encontrado con ID: " + articuloId));
            existing.setArticulo(articulo);
        }

        // Actualizar compra si es necesario
        if (ca.getCompra() != null) {
            Integer compraId = ca.getCompra().getId();
            Compra compra = compraRepository.findById(compraId)
                    .orElseThrow(() -> new RuntimeException(
                            "Compra no encontrada con ID: " + compraId));
            existing.setCompra(compra);
        }

        return repository.save(existing);
    }

    // Obtener por ID
    public Compra_articulo getById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Compra_articulo no encontrado con ID: " + id));
    }

    // Listar todos
    public List<Compra_articulo> getAll() {
        return repository.findAll();
    }

    // Eliminar por ID
    @Transactional
    public void delete(Integer id) {
        Compra_articulo existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Compra_articulo no encontrado con ID: " + id));
        repository.delete(existing);
    }
}

