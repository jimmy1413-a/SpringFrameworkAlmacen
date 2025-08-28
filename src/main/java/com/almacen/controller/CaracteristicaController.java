package com.almacen.controller;

import com.almacen.entity.Caracteristica;
import com.almacen.service.CaracteristicaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/caracteristica")
public class CaracteristicaController {

    @Autowired
    private CaracteristicaService service;

    // Crear
    @PostMapping
    public ResponseEntity<Caracteristica> create(@RequestBody Caracteristica caracteristica) {
        Caracteristica nueva = service.create(caracteristica);
        return ResponseEntity.ok(nueva);
    }

    // Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<Caracteristica> update(
            @PathVariable Integer id,
            @RequestBody Caracteristica caracteristica) {
        Caracteristica actualizada = service.actualizar(id, caracteristica);
        if (actualizada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actualizada);
    }

    // Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<Caracteristica> getById(@PathVariable Integer id) {
        Caracteristica c = service.getById(id);
        return ResponseEntity.ok(c);
    }

    // Listar todas
    @GetMapping
    public ResponseEntity<List<Caracteristica>> getAll() {
        List<Caracteristica> lista = service.getAll();
        return ResponseEntity.ok(lista);
    }

    // Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}