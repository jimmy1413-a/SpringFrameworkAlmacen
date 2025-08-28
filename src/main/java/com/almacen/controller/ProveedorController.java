package com.almacen.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.almacen.entity.Proveedor;
import com.almacen.service.ProveedorService;

@RestController
@RequestMapping("/proveedores")
@CrossOrigin(origins = "*") 
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    // Crear
    @PostMapping
    public ResponseEntity<Proveedor> guardar(@RequestBody Proveedor proveedor) {
        Proveedor nuevo = proveedorService.guardar(proveedor);
        return ResponseEntity.ok(nuevo);
    }

    // Listar
    @GetMapping
    public ResponseEntity<List<Proveedor>> listar() {
        return ResponseEntity.ok(proveedorService.List());
    }

    // Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<Proveedor> actualizar(@PathVariable Integer id, @RequestBody Proveedor proveedor) {
        Proveedor actualizado = proveedorService.actualizar(id, proveedor);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        boolean eliminado = proveedorService.eliminar(id);
        if (eliminado) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
