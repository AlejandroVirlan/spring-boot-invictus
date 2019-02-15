package com.avb.springboot.app.models.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avb.springboot.app.models.dao.ReservaDAO;
import com.avb.springboot.app.models.entity.JuegoMesa;
import com.avb.springboot.app.models.entity.Reserva;
import com.avb.springboot.app.models.entity.Socio;

/* Clase Service "ReservaService" donde implementamos los métodos 
 * de su interface y le añadimos la funcionalidad a cada uno */

@Service
public class ReservaServiceImpl implements ReservaService {

	// Inyección de dependencia de la clase DAO "ReservaDAO"
	@Autowired
	private ReservaDAO reservaDao;
	
	// Inyección de dependencia de la clase Service "SocioService"
	@Autowired
	SocioService socioService;
	
	// Inyección de dependencia de la clase Service "JuegoMesaService"
	@Autowired
	JuegoMesaService juegoService;
	
	// Método que busca una reserva en la base de datos por su id.
	@Override
	@Transactional(readOnly=true)
	public Optional<Reserva> findById(Long id) {
		return reservaDao.findById(id);
	}

	/* Método que busca todos las reservas en la base de datos y 
	 los devuelve con un paginador */
	@Override
	@Transactional(readOnly=true)
	public Page<Reserva> findAllPage(Pageable pageable) {
		return reservaDao.findAll(pageable);
	}

	/* Método que crea una nueva reserva en la base de datos
	 (obteniendo el socio y juego que ha reservado) */
	@Override
	@Transactional
	public Reserva create(Reserva reservaToCreate, Long idSocio, Long idJuego) {
		final Optional<Socio> socio = socioService.findById(idSocio);
		final Optional<JuegoMesa> juego = juegoService.findById(idJuego);
		reservaToCreate.setSocio(socio.get());
		reservaToCreate.setJuegoMesa(juego.get());
		
		return reservaDao.save(reservaToCreate);
	}

	// Método que actualiza una reserva en la base de datos 
	@Override
	@Transactional
	public void update(Reserva reservaToUpdate) {
		reservaDao.saveAndFlush(reservaToUpdate);
	}

	// Método que elimina una reserva en la base de datos 	
	@Override
	@Transactional
	public void delete(Long id) {
		reservaDao.deleteById(id);
	}

}
