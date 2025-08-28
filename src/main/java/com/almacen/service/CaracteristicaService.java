package com.almacen.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.almacen.entity.Caracteristica;
import com.almacen.repository.CaracteristicaRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class CaracteristicaService {
	
	@Autowired
    private CaracteristicaRepository repository;
    

    @Transactional
    public Caracteristica create(Caracteristica caracteristica) {
        if (caracteristica== null || caracteristica.getDescripcion().isEmpty()) {
            throw new IllegalArgumentException("La descripción no puede estar vacía");
        }
        return repository.save(caracteristica);
    }
    
    @Transactional
    public Caracteristica actualizar(Integer id,Caracteristica request) {
		Optional<Caracteristica> f= repository.findById(id);
			
			if( f.isPresent() ) {
				Caracteristica nuevo = f.get();
				nuevo.setDescripcion(request.getDescripcion());
				return repository.save(nuevo);
			}else {
				return null;
			}
		}
   

    // Obtener por ID
    public Caracteristica getById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Característica no encontrada con ID: " + id));
    }

    // Listar todas las características
    public List<Caracteristica> getAll() {
        return repository.findAll();
    }

    // Eliminar por ID
    @Transactional
    public void delete(Integer id) {
        Caracteristica existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Característica no encontrada con ID: " + id));
        repository.delete(existing);
    }
}
