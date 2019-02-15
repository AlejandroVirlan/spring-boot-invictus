package com.avb.springboot.app.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//Clase Entity "JuegoMesa" con sus propiedades y restricciones para definir la tabla "juegos_mesa" en la base de datos

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="juegos_mesa")
public class JuegoMesa implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	@Column(length=60, nullable=false)
	@Size(min = 1, max = 60)
	private String nombre;
	
	@Column(nullable=true)
	private String resumen;
	
	@Digits(integer=5,fraction=2)
	@Column(precision = 5, scale = 2, nullable=false)
	private Double precio;
	
	private Boolean vendido;
	
	@Column(length=100, nullable=true)
	private String video;
	
	@NotEmpty
	@Column(nullable=false)
	private String slug;
	
	private static final long serialVersionUID = 1L;

}
