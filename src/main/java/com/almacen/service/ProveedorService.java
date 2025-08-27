package com.almacen.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.almacen.entity.Categoria;
import com.almacen.entity.Proveedor;
import com.almacen.repository.ProveedorRepository;

@Service
public class ProveedorService {
	
	@Autowired
	private ProveedorRepository pR;
	
	public Proveedor guardar(Proveedor r) {
		Proveedor nuevo = new Proveedor();
		nuevo.setNombre(r.getNombre());
		nuevo.setCorreo(r.getCorreo());
		nuevo.setDireccion(r.getDireccion());
		nuevo.setTelefono(r.getTelefono());
		
		return pR.save(nuevo);
	}
	
	public List<Proveedor> List(){
		return pR.findAll();
		
	}
	
	public Proveedor actualizar(Integer id,Proveedor request) {
		Optional<Proveedor> f= pR.findById(id);
			
			if( f.isPresent() ) {
				Proveedor nuevo = f.get();
				nuevo.setNombre(request.getNombre());
				nuevo.setDireccion(request.getDireccion());
				nuevo.setCorreo(request.getCorreo());
				nuevo.setTelefono(request.getTelefono());
				return pR.save(nuevo);
			}else {
				return null;
			}
		}
	
		public boolean eliminar(Integer id) {
			if(pR.existsById(id)) {
				pR.deleteById(id);
				return true;
			}else {
				return false;																																																																																																																																																																																																																																																																																																													
			}
			
		}
	}

