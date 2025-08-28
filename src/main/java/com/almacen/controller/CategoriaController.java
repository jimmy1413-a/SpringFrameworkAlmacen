package com.almacen.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.almacen.entity.Categoria;
import com.almacen.service.CategoriaService;

@RestController
@RequestMapping("/api/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    // Listar todas las categorías
    @GetMapping
    public ResponseEntity<List<Categoria>> listar() {
        List<Categoria> categorias = categoriaService.listar();
        return ResponseEntity.ok(categorias);
    }

    // Obtener categoría por ID
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> getById(@PathVariable Integer id) {
        Categoria categoria = categoriaService.get(id);
        if (categoria != null) {
            return ResponseEntity.ok(categoria);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Crear nueva categoría
    @PostMapping
    public ResponseEntity<Categoria> crear(@RequestBody Categoria categoria) {
        Categoria nueva = categoriaService.guardar(categoria);
        return ResponseEntity.ok(nueva);
    }

    // Actualizar categoría existente
    @PutMapping("/{id}")
    public ResponseEntity<Categoria> actualizar(@PathVariable Integer id, @RequestBody Categoria categoria) {
        Categoria actualizada = categoriaService.actualizar(id, categoria);
        if (actualizada != null) {
            return ResponseEntity.ok(actualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar categoría
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        boolean eliminado = categoriaService.eliminar(id);
        if (eliminado) {
            return ResponseEntity.ok("Categoría eliminada correctamente");
        } else {
            return ResponseEntity.badRequest().body("No se encontró la categoría con ID: " + id);
        }
    }
}
