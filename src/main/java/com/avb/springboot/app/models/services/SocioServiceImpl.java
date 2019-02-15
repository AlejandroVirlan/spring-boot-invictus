package com.avb.springboot.app.models.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avb.springboot.app.models.dao.SocioDAO;
import com.avb.springboot.app.models.dao.UsuarioDAO;
import com.avb.springboot.app.models.entity.Socio;
import com.avb.springboot.app.models.entity.Usuario;

/* Clase Service "SocioService" donde implementamos los métodos 
 * de su interface y le añadimos la funcionalidad a cada uno */

@Service
public class SocioServiceImpl implements SocioService{

	// Inyección de dependencia de la clase DAO "SocioDAO"
	@Autowired
	private SocioDAO socioDao;
	
	// Inyección de dependencia de la clase DAO "UsuarioDAO"
	@Autowired
	private UsuarioDAO usuarioDao;
	
	// Inyección de dependencia de la clase Service "UsuarioService"
	@Autowired
	private UsuarioService usuarioService;

	// Método que busca un socio en la base de datos por su id.
	@Override
	@Transactional(readOnly=true)
	public Optional<Socio> findById(Long id) {
		return socioDao.findById(id);
	}
	
	/* Método que busca todos los socios en la base de datos y 
	 los devuelve con un paginador */
	@Override
	@Transactional(readOnly=true)
	public Page<Socio> findAllPage(Pageable pageable) {
		return socioDao.findAll(pageable);
	}

	/* Método que crea un nuevo socio en la base de datos
	 (creando a su vez un registro en la tabla usuarios de la base de datos) */
	@Override
	@Transactional
	public Socio create(Socio socioToCreate) {
		usuarioDao.save(socioToCreate.getUsuario());
		final Optional<Usuario> usuario = usuarioService.findById(socioToCreate.getUsuario().getId());
		socioToCreate.setUsuario(usuario.get());
		return socioDao.save(socioToCreate);
	}

	// Método que actualiza un socio en la base de datos 
	@Override
	@Transactional
	public void update(Socio socioToUpdate) {
		socioDao.saveAndFlush(socioToUpdate);
		
	}
}
