package com.avb.springboot.app.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.avb.springboot.app.models.entity.Socio;
import com.avb.springboot.app.models.entity.Usuario;

/* La interface de Acceso a Datos (DAO) que hereda de "JpaRepository" 
del Entity "Socio" para poder acceder a los métodos para poder 
realizar las operaciones de un CRUD entre otras funcionalidades 
personalizadas que se les quiera implementar.

En este caso uno:

*/

public interface SocioDAO extends JpaRepository<Socio, Long>{
	
	//Método que busca a un usuario
	Socio findByUsuario(Usuario usuario);
}
