package com.almacen.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.almacen.entity.Categoria;
import com.almacen.repository.CategoriaRepository;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository cR;
	
	public Categoria guardar(Categoria r) {
		Categoria nuevo = new Categoria();
		nuevo.setDescripcion(r.getDescripcion());
		
		return cR.save(nuevo);	
	}
	
	public List<Categoria> listar(){
		return cR.findAll();
	}
	
	public Categoria get(Integer id) {
		return cR.findById(id).orElse(null);
	}
	
	public Categoria actualizar(Integer id,Categoria request) {
		Optional<Categoria> f= cR.findById(id);
		
		if( f.isPresent() ) {
			Categoria nuevo = f.get();
			nuevo.setDescripcion(request.getDescripcion());
			return cR.save(nuevo);
		}else {
			return null;
		}
	}
	
	public boolean eliminar(Integer id) {
		if(cR.existsById(id)) {
        	cR.deleteById(id);
            return true;
        }
        return false;
    }
	
}
	
	


