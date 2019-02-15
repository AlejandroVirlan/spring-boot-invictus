package com.avb.springboot.app.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.avb.springboot.app.models.entity.JuegoMesa;

/* La interface de Acceso a Datos (DAO) que hereda de "JpaRepository" 
 del Entity "JuegoMesa" para poder acceder a los métodos para poder 
 realizar las operaciones de un CRUD entre otras funcionalidades 
 personalizadas que se les quiera implementar.*/

public interface JuegoMesaDAO extends JpaRepository<JuegoMesa, Long>{

}
