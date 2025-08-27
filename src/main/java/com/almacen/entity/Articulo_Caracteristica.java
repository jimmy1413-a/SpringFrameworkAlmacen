package com.almacen.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "articulo_caracteristica")
@Data
public class Articulo_Caracteristica {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "articulo",referencedColumnName = "id")
	private Articulo articulo;
	
	
	@ManyToOne
	@JoinColumn(name = "caracteristica",referencedColumnName = "id")
	private Caracteristica caracteristica;
	
	
	

}
