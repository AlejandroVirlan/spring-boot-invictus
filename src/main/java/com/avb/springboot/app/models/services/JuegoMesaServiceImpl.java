package com.avb.springboot.app.models.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avb.springboot.app.models.dao.JuegoMesaDAO;
import com.avb.springboot.app.models.entity.JuegoMesa;

/* Clase Service "JuegoMesaService" donde implementamos los métodos 
 * de su interface y le añadimos la funcionalidad a cada uno */

@Service
public class JuegoMesaServiceImpl implements JuegoMesaService {

	// Inyección de dependencia de la clase DAO "JuegoMesaDAO"
	@Autowired
	private JuegoMesaDAO juegoDao;
	
	// Método que busca un juego de mesa en la base de datos por su id.
	@Override
	@Transactional(readOnly=true)
	public Optional<JuegoMesa> findById(Long id) {
		return juegoDao.findById(id);
	}

	/* Método que busca todos los juegos de mesa en la base de datos y 
	 los devuelve con un paginador */
	@Override
	@Transactional(readOnly=true)
	public Page<JuegoMesa> findAllPage(Pageable p) {
		return juegoDao.findAll(p);
	}
	
	// Método que crea un nuevo juego de mesa en la base de datos 
	@Override
	@Transactional
	public JuegoMesa create(JuegoMesa juegoToCreate) {
		return juegoDao.save(juegoToCreate);
	}
	
	// Método que actualiza un juego de mesa en la base de datos 
	@Override
	@Transactional
	public void update(JuegoMesa juegoToUpdate) {
		juegoDao.saveAndFlush(juegoToUpdate);
	}
}
