package com.almacen.controller;

import com.almacen.entity.Articulo_Caracteristica;
import com.almacen.service.ArticuloCaracteristicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articulo-caracteristica")
public class ArticuloCaracteristicaController {

    @Autowired
    private ArticuloCaracteristicaService service;

    // Crear nueva relación Articulo - Caracteristica
    @PostMapping
    public ResponseEntity<Articulo_Caracteristica> create(@RequestBody Articulo_Caracteristica ac) {
        Articulo_Caracteristica nuevo = service.create(ac);
        return ResponseEntity.ok(nuevo);
    }

    // Actualizar relación
    @PutMapping("/{id}")
    public ResponseEntity<Articulo_Caracteristica> update(@PathVariable Integer id,
                                                          @RequestBody Articulo_Caracteristica ac) {
        ac.setId(id);
        Articulo_Caracteristica actualizado = service.update(ac);
        return ResponseEntity.ok(actualizado);
    }

    // Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<Articulo_Caracteristica> getById(@PathVariable Integer id) {
        Articulo_Caracteristica ac = service.getById(id);
        return ResponseEntity.ok(ac);
    }

    // Listar todas
    @GetMapping
    public ResponseEntity<List<Articulo_Caracteristica>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    // Eliminar por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok("Relación eliminada correctamente");
    }
}
