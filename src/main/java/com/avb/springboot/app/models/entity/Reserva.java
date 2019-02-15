package com.avb.springboot.app.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//Clase Entity "Reserva" con sus propiedades, restricciones y relaciones para definir la tabla "reservas" en la base de datos

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="reservas")
public class Reserva implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JsonManagedReference
	private Socio socio;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd-MM-yyyy hh:mm:ss")
	@Column(name="fecha_hora")
	private Date fechaHora;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="juego_mesa_id")
	private JuegoMesa juegoMesa;
	
	@PrePersist
	public void prePersist() {
		fechaHora = new Date();
	}
	
	private static final long serialVersionUID = 1L;

}
