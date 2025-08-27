package com.almacen.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "compra_articulo")
@Data
public class Compra_articulo {
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	
	@ManyToOne
	@JoinColumn(name = "articulo",referencedColumnName = "id")
	private Articulo articulo;
	
	@ManyToOne
	@JoinColumn(name ="compra",referencedColumnName = "id")
	private Compra compra;
	
	
	private Integer cantidad;
	
	
	private Integer valor_unitario;
	
	
	private Integer valor;

}
