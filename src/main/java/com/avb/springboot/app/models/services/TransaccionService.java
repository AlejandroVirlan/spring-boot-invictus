package com.avb.springboot.app.models.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.avb.springboot.app.models.entity.Transaccion;

/* La interface de Servicios (Services) del Entity "Transaccion", 
 * donde se implementa los metodos que va a contener las funciones
 * del DAO "TransaccionDAO" */

public interface TransaccionService {
	
	Optional<Transaccion> findById(Long id);
	
	Page<Transaccion> findAllPage(Pageable p);
	
	Transaccion create (Transaccion transaccionToCreate, Long idSocio);
	
	void update(Transaccion transaccionToUpdate);
	
	void delete(Long id);
		
	List<Transaccion> encontrarFacturasEntreFechas(Date fechaInicio, Date fechaFin);
	
	Double calcularTotalTransaccionesFechas(Date fechaInicio, Date fechaFin);

}
