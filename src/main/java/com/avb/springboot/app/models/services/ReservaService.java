package com.avb.springboot.app.models.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.avb.springboot.app.models.entity.Reserva;

/* La interface de Servicios (Services) del Entity "Reserva", 
 * donde se implementa los metodos que va a contener las funciones
 * del DAO "ReservaDAO" */

public interface ReservaService {

	Optional<Reserva> findById(Long id);
	
	Page<Reserva> findAllPage(Pageable p);
	
	Reserva create (Reserva reservaToCreate, Long idSocio, Long idJuego);
	
	void update(Reserva reservaToUpdate);
	
	void delete(Long id);
}
