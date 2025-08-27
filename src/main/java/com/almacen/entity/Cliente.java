package com.almacen.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Entity 
@Data
@Table(name="cliente")

public class Cliente {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	
	@NotBlank(message = "El nombre es obligatorio")
	@Size(max=10,min=2, message = "EL nombre debe de tener entre 2 y 10 caracteres")
	private String nombre;
	
	@Email(message = "EL formato de email no es correcto")
	@NotBlank(message = "EL email esobligatorio")
	private String Email;
	
	@NotBlank(message = "La dirección es obligatoria")
	private String Direccion;
	
	@NotBlank(message = "El telefono es obligatorio")
	private String Telefono;

}