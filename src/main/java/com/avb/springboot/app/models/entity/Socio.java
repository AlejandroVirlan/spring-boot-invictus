package com.avb.springboot.app.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

//import com.fasterxml.jackson.annotation.JsonBackReference;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

//Clase Entity "Socio" con sus propiedades, restricciones y relaciones para definir la tabla "socios" en la base de datos

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name="socios")
public class Socio implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	@Column(length=9, nullable=false)
	@Size(min = 9, max = 9)
	private String dni;
	
	@NotEmpty
	@Column(length=30,nullable=false)
	@Size(min = 1, max = 30)
	private String nombre;
	
	@NotEmpty
	@Column(length=30,nullable=false)
	@Size(min = 1, max = 30)
	private String apellido1;
	
	@NotEmpty
	@Column(length=30,nullable=false)
	@Size(min = 1, max = 30)
	private String apellido2;
	
	@NotEmpty
	@Column(length=16,nullable=false)
	@Size(min = 1, max = 16)
	private String movil;
	
	@Column(length=100,nullable=true)
	private String foto;
	
	@OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="usuario_id")
	private Usuario usuario;
	
	@OneToMany(mappedBy="socio" ,fetch=FetchType.LAZY)
	private List<Transaccion> transacciones;
	
	@OneToMany(mappedBy="socio" ,fetch=FetchType.LAZY)
	private List<Reserva> reservas;

	public Socio() {
		transacciones = new ArrayList<Transaccion>();
		reservas = new ArrayList<Reserva>();
	}
	
	public void addTransaccion(Transaccion transaccion) {
		transacciones.add(transaccion);
	}
	
	public void addReserva(Reserva reserva) {
		reservas.add(reserva);
	}
	
	private static final long serialVersionUID = 1L;

}
