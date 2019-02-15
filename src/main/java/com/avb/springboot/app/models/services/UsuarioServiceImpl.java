package com.avb.springboot.app.models.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avb.springboot.app.models.dao.UsuarioDAO;
import com.avb.springboot.app.models.entity.Usuario;

/* Clase Service "UsuarioService" donde implementamos los métodos 
 * de su interface y le añadimos la funcionalidad a cada uno */

@Service
public class UsuarioServiceImpl implements UsuarioService {

	// Inyección de dependencia de la clase DAO "UsuarioDAO"
	@Autowired
	private UsuarioDAO usuarioDao;
	
	// Método que busca un usuario en la base de datos por su id.
	@Override
	@Transactional(readOnly=true)
	public Optional<Usuario> findById(Long id) {
		return usuarioDao.findById(id);
	}
	
	/* Método que busca todos los usuarios en la base de datos y 
	 los devuelve con un paginador */
	@Override
	@Transactional(readOnly=true)
	public Page<Usuario> findAllPage(Pageable pageable) {
		return usuarioDao.findAll(pageable);
	}
	
	// Método que crea un nuevo usuario en la base de datos 
	@Override
	@Transactional
	public Usuario create(Usuario usuarioToCreate) {
		return usuarioDao.save(usuarioToCreate);
	}
	
	// Método que actualiza un usuario en la base de datos 
	@Override
	@Transactional
	public void update(Usuario usuarioToUpdate) {
		usuarioDao.saveAndFlush(usuarioToUpdate);
	}
	
	// Método que busca un usuario en la base de datos por su nombre de usuario
	@Override
	@Transactional(readOnly=true)
	public Optional<Usuario> findByUsername(String username) {
		return usuarioDao.findByUsername(username);
	}

	// Método que comprueba si existe un usuario con el nombre de usuario que se le indique
	@Override
	@Transactional(readOnly=true)
	public Boolean existsByUsername(String username) {
		return usuarioDao.existsByUsername(username);
	}

	// Método que comprueba si existe un usuario con el email que se le indique
	@Override
	@Transactional(readOnly=true)
	public Boolean existsByEmail(String email) {
		return usuarioDao.existsByEmail(email);
	}
}
