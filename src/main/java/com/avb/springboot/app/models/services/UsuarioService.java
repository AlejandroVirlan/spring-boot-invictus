package com.avb.springboot.app.models.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.avb.springboot.app.models.entity.Usuario;

/* La interface de Servicios (Services) del Entity "Usuario", 
 * donde se implementa los metodos que va a contener las funciones
 * del DAO "UsuarioDAO" */

public interface UsuarioService {
	
	Optional<Usuario> findById(Long id);
	
	Optional<Usuario> findByUsername(String username);
	
	Boolean existsByUsername(String username);
	
	Boolean existsByEmail(String email);
		
	Page<Usuario> findAllPage(Pageable pageable);
	
	Usuario create (Usuario usuarioToCreate);
	
	void update(Usuario usuarioToUpdate);
	
}
