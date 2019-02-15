package com.avb.springboot.app.models.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.avb.springboot.app.models.entity.Transaccion;

/* La interface de Acceso a Datos (DAO) que hereda de "JpaRepository" 
del Entity "Transaccion" para poder acceder a los métodos para poder 
realizar las operaciones de un CRUD entre otras funcionalidades 
personalizadas que se les quiera implementar.

En este caso tenemos dos:

*/

public interface TransaccionDAO extends JpaRepository<Transaccion, Long>{
	
	//Método que busca las transacciones entre dos fechas
	@Query("SELECT t from Transaccion t WHERE DATE(t.fecha) BETWEEN ?1 AND ?2")
	List<Transaccion> encontrarFacturasEntreFechas(Date fechaInicio, Date fechaFin);
	
	//Método que devuelve el sumatorio de todas las transacciones entre dos fechas
	@Query("SELECT SUM(t.importe) from Transaccion t WHERE DATE(t.fecha) BETWEEN ?1 AND ?2")
	Double calcularTotalTransaccionesFechas(Date fechaInicio, Date fechaFin);
	
}
