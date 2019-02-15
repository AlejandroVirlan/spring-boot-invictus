package com.avb.springboot.app.models.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.avb.springboot.app.models.entity.Socio;

/* La interface de Servicios (Services) del Entity "Socio", 
 * donde se implementa los metodos que va a contener las funciones
 * del DAO "SocioDAO" */

public interface SocioService {
	
	Optional<Socio> findById(Long id);
		
	Page<Socio> findAllPage(Pageable pageable);
	
	Socio create (Socio socioToCreate);
	
	void update(Socio socioToUpdate);
		
}
