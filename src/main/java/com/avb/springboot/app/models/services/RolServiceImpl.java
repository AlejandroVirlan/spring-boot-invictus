package com.avb.springboot.app.models.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avb.springboot.app.models.dao.RolDAO;
import com.avb.springboot.app.models.entity.NombreRol;
import com.avb.springboot.app.models.entity.Rol;

/* Clase Service "RolService" donde implementamos los métodos 
 * de su interface y le añadimos la funcionalidad a cada uno */

@Service
public class RolServiceImpl implements RolService {
	
	// Inyección de dependencia de la clase DAO "RolDAO"
	@Autowired
	private RolDAO rolDao;

	// Método que busca un Rol en la base de datos por nombre.
	@Override
	@Transactional(readOnly=true)
	public Optional<Rol> findByNombreRol(NombreRol nombreRol) {
		return rolDao.findByNombreRol(nombreRol);
	}

}
