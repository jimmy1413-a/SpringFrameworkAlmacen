package com.almacen.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
@Table(name = "venta_articulo")
@Data
public class Venta_articulo {
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	
	@ManyToOne
	@JoinColumn(name = "venta",referencedColumnName = "id")
	@JsonBackReference
	private Venta venta;
	
	
	@ManyToOne
	@JoinColumn(name = "articulo",referencedColumnName = "id")
	private Articulo articulo;
	
	
	private Integer cantidad;
	
	private Integer valor;
}
