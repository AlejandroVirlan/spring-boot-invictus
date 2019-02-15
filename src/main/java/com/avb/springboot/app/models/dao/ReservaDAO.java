package com.avb.springboot.app.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.avb.springboot.app.models.entity.Reserva;

/* La interface de Acceso a Datos (DAO) que hereda de "JpaRepository" 
del Entity "Reserva" para poder acceder a los métodos para poder 
realizar las operaciones de un CRUD entre otras funcionalidades 
personalizadas que se les quiera implementar.*/

public interface ReservaDAO extends JpaRepository<Reserva, Long> {

}
