package com.avb.springboot.app.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//Clase Entity "Transaccion" con sus propiedades, restricciones y relaciones para definir la tabla "transacciones" en la base de datos

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="transacciones")
public class Transaccion implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	@Column(nullable=true)
	private String concepto;
	
	@Digits(integer=5, fraction=2)
	@Column(precision = 5, scale = 2, nullable=false)
	private Double importe;
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd-MM-yyyy hh:mm:ss")
	private Date fecha;
	
	@Column(name="escaneo_factura", length=100, nullable=true)
	private String escaneoFactura;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JsonManagedReference
	private Socio socio;
	
	@PrePersist
	public void prePersist() {
		fecha = new Date();
	}
	 
	private static final long serialVersionUID = 1L;

}
