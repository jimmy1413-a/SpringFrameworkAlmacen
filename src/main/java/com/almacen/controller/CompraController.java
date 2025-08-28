package com.almacen.controller;

import com.almacen.entity.Compra;
import com.almacen.service.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compras")
public class CompraController {

    @Autowired
    private CompraService compraService;

    // Crear nueva compra
    @PostMapping
    public ResponseEntity<Compra> guardar(@RequestBody Compra compra) {
        Compra nueva = compraService.guardar(compra);
        return ResponseEntity.ok(nueva);
    }

    // Listar todas las compras
    @GetMapping
    public ResponseEntity<List<Compra>> listar() {
        return ResponseEntity.ok(compraService.listar());
    }

    // Obtener compra por ID
    @GetMapping("/{id}")
    public ResponseEntity<Compra> get(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(compraService.get(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Actualizar compra
    @PutMapping("/{id}")
    public ResponseEntity<Compra> actualizar(@PathVariable Integer id, @RequestBody Compra compra) {
        try {
            return ResponseEntity.ok(compraService.actualizar(id, compra));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar compra
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (compraService.eliminar(id)) {
            return ResponseEntity.noContent().build(); // 204 eliminado con éxito
        }
        return ResponseEntity.notFound().build();
    }
}
