package com.avb.springboot.app.models.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.avb.springboot.app.models.entity.Usuario;

/* La interface de Acceso a Datos (DAO) que hereda de "JpaRepository" 
del Entity "Usuario" para poder acceder a los métodos para poder 
realizar las operaciones de un CRUD entre otras funcionalidades 
personalizadas que se les quiera implementar.

En este caso tenemos tres:

*/

public interface UsuarioDAO extends JpaRepository<Usuario, Long>{

	//Método que busca al usuario por su nombre de usuario (username)
	Optional<Usuario> findByUsername(String username);
	
	//Método que comprueba si existe un usuario por su nombre de usuario (username)
	Boolean existsByUsername(String username);
	
	//Método que comprueba si existe un usuario por su email
	Boolean existsByEmail(String email);
}
