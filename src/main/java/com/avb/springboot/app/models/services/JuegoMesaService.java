package com.avb.springboot.app.models.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.avb.springboot.app.models.entity.JuegoMesa;

/* La interface de Servicios (Services) del Entity "JuegoMesa", 
 * donde se implementa los metodos que va a contener las funciones
 * del DAO "JuegoMesaDAO" */

public interface JuegoMesaService {

	Optional<JuegoMesa> findById(Long id);
	
	Page<JuegoMesa> findAllPage(Pageable pageable);
	
	JuegoMesa create (JuegoMesa juegoToCreate);
	
	void update(JuegoMesa juegoToUpdate);
		
}
