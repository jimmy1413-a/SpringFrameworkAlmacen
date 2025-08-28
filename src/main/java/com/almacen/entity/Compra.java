package com.almacen.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = " compra")
@Data

public class Compra {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	
	private LocalDateTime fecha;
	
	
	@ManyToOne
	@JoinColumn(name ="Proveedor",referencedColumnName = "id")
	private Proveedor proveedor;
	
	
	@OneToMany(mappedBy = "compra",cascade = CascadeType.ALL,orphanRemoval = true)
	@JsonManagedReference
	private List<Compra_articulo> detalles = new ArrayList<>();
	
	
	@NotNull(message = "El precio de compra es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio de compra debe ser mayor a cero")
    @Digits(integer=10, fraction=2, message = "El precio debe tener hasta 10 dígitos enteros y 2 decimales")
    private BigDecimal valor;
	
}
