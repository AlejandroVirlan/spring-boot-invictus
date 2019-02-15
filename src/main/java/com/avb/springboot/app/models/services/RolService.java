package com.avb.springboot.app.models.services;

import java.util.Optional;

import com.avb.springboot.app.models.entity.NombreRol;
import com.avb.springboot.app.models.entity.Rol;

/* La interface de Servicios (Services) del Entity "Rol", 
 * donde se implementa los metodos que va a contener las funciones
 * del DAO "RolDAO" */

public interface RolService {
	Optional<Rol> findByNombreRol(NombreRol nombreRol);
}
