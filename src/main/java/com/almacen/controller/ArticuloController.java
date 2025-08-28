package com.almacen.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.almacen.apiResponse.ApiResponse;
import com.almacen.entity.Articulo;

import com.almacen.service.ArticuloService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/articulo")
public class ArticuloController {

    @Autowired
    private ArticuloService articuloService;

    // Crear Articulo
    @PostMapping
    public ResponseEntity<ApiResponse<?>> guardar(@Valid @RequestBody Articulo request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMsg = bindingResult.getFieldError().getDefaultMessage();
            return ResponseEntity.badRequest().body(new ApiResponse<>(errorMsg, HttpStatus.BAD_REQUEST.value(), null));
        }

        try {
            Articulo creado = articuloService.guardar(request);
            return ResponseEntity.ok(new ApiResponse<>("Articulo creado correctamente.", HttpStatus.OK.value(), creado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), null));
        }
    }

    // Listar Articulos
    @GetMapping
    public ResponseEntity<ApiResponse<?>> listar() {
        List<Articulo> articulos = articuloService.listar();
        return ResponseEntity.ok(new ApiResponse<>("Listado obtenido correctamente.", HttpStatus.OK.value(), articulos));
    }

    // Obtener Articulo por ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> get(@PathVariable Integer id) {
        Articulo articulo = articuloService.get(id);
        if (articulo != null) {
            return ResponseEntity.ok(new ApiResponse<>("Articulo encontrado.", HttpStatus.OK.value(), articulo));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("Articulo no encontrado.", HttpStatus.NOT_FOUND.value(), null));
        }
    }

    // Actualizar Articulo
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> actualizar(@PathVariable Integer id,
                                                     @Valid @RequestBody Articulo request,
                                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMsg = bindingResult.getFieldError().getDefaultMessage();
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(errorMsg, HttpStatus.BAD_REQUEST.value(), null));
        }

        Articulo actualizado = articuloService.actualizar(id, request);
        if (actualizado != null) {
            return ResponseEntity.ok(new ApiResponse<>("Articulo actualizado.", HttpStatus.OK.value(), actualizado));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("Articulo no encontrado para actualizar.", HttpStatus.NOT_FOUND.value(), null));
        }
    }

    // Eliminar Articulo
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> eliminar(@PathVariable Integer id) {
        boolean eliminado = articuloService.eliminar(id);
        if (eliminado) {
            return ResponseEntity.ok(new ApiResponse<>("Articulo eliminado.", HttpStatus.OK.value(), null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("Articulo no encontrado para eliminar.", HttpStatus.NOT_FOUND.value(), null));
        }
    }
}