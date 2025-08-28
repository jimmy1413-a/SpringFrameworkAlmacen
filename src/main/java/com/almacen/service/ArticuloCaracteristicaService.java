package com.almacen.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.almacen.entity.Articulo;
import com.almacen.entity.Articulo_Caracteristica;
import com.almacen.entity.Caracteristica;

import com.almacen.repository.ArticuloRepository;
import com.almacen.repository.Articulo_CaracteristicaRepository;
import com.almacen.repository.CaracteristicaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArticuloCaracteristicaService {
	
	
	@Autowired
    private  Articulo_CaracteristicaRepository repository;
	
	@Autowired
    private ArticuloRepository articuloRepository;
	
	@Autowired
    private CaracteristicaRepository caracteristicaRepository;

    // Crear una nueva relación Articulo_Caracteristica
    @Transactional
    public Articulo_Caracteristica create(Articulo_Caracteristica ac) {
        // Validar artículo
        if (ac.getArticulo() == null || ac.getArticulo().getId() == null) {
            throw new IllegalArgumentException("Debe proporcionar un artículo válido");
        }
        Articulo articulo = articuloRepository.findById(ac.getArticulo().getId())
                .orElseThrow(() -> new RuntimeException(
                        "Articulo no encontrado con ID: " + ac.getArticulo().getId()));
        ac.setArticulo(articulo);

        // Validar caracteristica
        if (ac.getCaracteristica() == null || ac.getCaracteristica().getId()== null) {
            throw new IllegalArgumentException("Debe proporcionar una característica válida");
        }
        Caracteristica caracteristica = caracteristicaRepository.findById(ac.getCaracteristica().getId())
                .orElseThrow(() -> new RuntimeException(
                        "Característica no encontrada con ID: " + ac.getCaracteristica().getId()));
        ac.setCaracteristica(caracteristica);

        return repository.save(ac);
    }

    // Actualizar una relación existente
    @Transactional
    public Articulo_Caracteristica update(Articulo_Caracteristica ac) {
        Articulo_Caracteristica existing = repository.findById(ac.getId())
                .orElseThrow(() -> new RuntimeException(
                        "Articulo_Caracteristica no encontrado con ID: " + ac.getId()));

        // Actualizar artículo si se proporciona
        if (ac.getArticulo() != null && ac.getArticulo().getId() != null) {
            Articulo articulo = articuloRepository.findById(ac.getArticulo().getId())
                    .orElseThrow(() -> new RuntimeException(
                            "Articulo no encontrado con ID: " + ac.getArticulo().getId()));
            existing.setArticulo(articulo);
        }

        // Actualizar característica si se proporciona
        if (ac.getCaracteristica() != null && ac.getCaracteristica().getId() != null) {
            Caracteristica caracteristica = caracteristicaRepository.findById(ac.getCaracteristica().getId())
                    .orElseThrow(() -> new RuntimeException(
                            "Característica no encontrada con ID: " + ac.getCaracteristica().getId()));
            existing.setCaracteristica(caracteristica);
        }

        return repository.save(existing);
    }

    // Obtener por ID
    public Articulo_Caracteristica getById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Articulo_Caracteristica no encontrado con ID: " + id));
    }

    // Listar todas las relaciones
    public List<Articulo_Caracteristica> getAll() {
        return repository.findAll();
    }

    // Eliminar por ID
    @Transactional
    public void delete(Integer id) {
        Articulo_Caracteristica existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Articulo_Caracteristica no encontrado con ID: " + id));
        repository.delete(existing);
    }
}
