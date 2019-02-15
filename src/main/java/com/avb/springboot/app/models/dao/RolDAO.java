package com.avb.springboot.app.models.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.avb.springboot.app.models.entity.NombreRol;
import com.avb.springboot.app.models.entity.Rol;

/* La interface de Acceso a Datos (DAO) que hereda de "JpaRepository" 
del Entity "Rol" para poder acceder a los métodos para poder 
realizar las operaciones de un CRUD entre otras funcionalidades 
personalizadas que se les quiera implementar

En este caso buscaremos el nombre del rol o roles que tenga el Socio que se indique */
public interface RolDAO extends JpaRepository<Rol, Long> {
	Optional<Rol> findByNombreRol(NombreRol nombreRol);
}
