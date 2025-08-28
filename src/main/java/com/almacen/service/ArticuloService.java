package com.almacen.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.almacen.entity.Articulo;
import com.almacen.entity.Categoria;
import com.almacen.repository.ArticuloRepository;
import com.almacen.repository.CategoriaRepository;

@Service
public class ArticuloService {

    @Autowired
    private ArticuloRepository articuloRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    // Guardar Articulo
    public Articulo guardar(Articulo request) {
        Categoria categoria = categoriaRepository.findById(request.getCategoria().getId())
                .orElseThrow(() -> new RuntimeException(
                        "Categoria no encontrada con ID: " + request.getCategoria().getId()));

        request.setCategoria(categoria);
        return articuloRepository.save(request);
    }

    // Listar todos los Articulos
    public List<Articulo> listar() {
        return articuloRepository.findAll();
    }

    // Obtener Articulo por ID
    public Articulo get(Integer id) {
        return articuloRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Articulo no encontrado con ID: " + id));
    }

    // Actualizar Articulo
    public Articulo actualizar(Integer id, Articulo request) {
        Articulo actual = articuloRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Articulo no encontrado con ID: " + id));

        Categoria categoria = categoriaRepository.findById(request.getCategoria().getId())
                .orElseThrow(() -> new RuntimeException(
                        "Categoria no encontrada con ID: " + request.getCategoria().getId()));

        actual.setCategoria(categoria);
        actual.setNombre(request.getNombre());
        actual.setDescripcion(request.getDescripcion());
        actual.setCantidad(request.getCantidad());
        actual.setEstado(request.getEstado());

        return articuloRepository.save(actual);
    }

    // Eliminar Articulo
    public boolean eliminar(Integer id) {
        if (articuloRepository.existsById(id)) {
            articuloRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

 