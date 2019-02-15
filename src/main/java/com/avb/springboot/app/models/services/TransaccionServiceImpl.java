package com.avb.springboot.app.models.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avb.springboot.app.models.dao.TransaccionDAO;
import com.avb.springboot.app.models.entity.Socio;
import com.avb.springboot.app.models.entity.Transaccion;

/* Clase Service "TransaccionService" donde implementamos los métodos 
 * de su interface y le añadimos la funcionalidad a cada uno */

@Service
public class TransaccionServiceImpl implements TransaccionService {

	// Inyección de dependencia de la clase DAO "TransaccionDAO"
	@Autowired
	private TransaccionDAO transaccionDao;
	
	// Inyección de dependencia de la clase Service "SocioService"
	@Autowired
	SocioService socioService;
	
	// Método que busca una transacción en la base de datos por su id.
	@Override
	@Transactional(readOnly=true)
	public Optional<Transaccion> findById(Long id) {
		return transaccionDao.findById(id);
	}

	/* Método que busca todas las transacciones en la base de datos y 
	 los devuelve con un paginador */
	@Override
	@Transactional(readOnly=true)
	public Page<Transaccion> findAllPage(Pageable p) {
		return transaccionDao.findAll(p);
	}

	// Método que crea una transacción en la base de datos 
	@Override
	@Transactional
	public Transaccion create(Transaccion transaccionToCreate, Long idSocio) {
		final Optional<Socio> socio = socioService.findById(idSocio);
		transaccionToCreate.setSocio(socio.get());
		
		return transaccionDao.save(transaccionToCreate);
	}
	
	// Método que actualiza una transacción en la base de datos
	@Override
	@Transactional
	public void update(Transaccion transaccionToUpdate) {
		transaccionDao.saveAndFlush(transaccionToUpdate);
		
	}
	
	// Método que elimina una transacción en la base de datos 
	@Override
	@Transactional
	public void delete(Long id) {
		transaccionDao.deleteById(id);
	}

	// Método que busca todas las transacciones en la base de datos que existan entre dos fechas
	@Override	
	@Transactional(readOnly=true)
	public List<Transaccion> encontrarFacturasEntreFechas(Date fechaInicio, Date fechaFin) {
		return transaccionDao.encontrarFacturasEntreFechas(fechaInicio, fechaFin);
	}

	// Método que calcula la suma de todas las transacciones en la base de datos que existan entre dos fechas
	@Override
	@Transactional(readOnly=true)
	public Double calcularTotalTransaccionesFechas(Date fechaInicio, Date fechaFin) {	
		return transaccionDao.calcularTotalTransaccionesFechas(fechaInicio, fechaFin);
	}

}
