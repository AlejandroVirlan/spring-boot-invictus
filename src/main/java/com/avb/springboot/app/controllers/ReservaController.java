package com.avb.springboot.app.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avb.springboot.app.dto.ReservaDTO;
import com.avb.springboot.app.mapper.ReservaMapper;
import com.avb.springboot.app.models.entity.Reserva;
import com.avb.springboot.app.models.services.ReservaService;
//@CrossOrigin comunica con la app de Angular en este caso
@CrossOrigin(origins = "*")
//@RestController es la anotación que crea un servicio REST
@RestController
/* @RequestMapping es la anotación que se encarga de relacionar un método con una petición http,
 * en este caso, todas las peticiones de este controlador empezarán con /api/reservas.
 */
@RequestMapping("/api/reservas")
public class ReservaController {
	
	// Inyección de dependencia de la clase Service "ReservaService"
	@Autowired
	private ReservaService reservaService;
	
	// Inyección de dependencia de la clase Mapper "ReservaMapper"
	@Autowired
	ReservaMapper reservaMapper;

	// Método que lista todos las reservas de la base de datos en un paginador
	@GetMapping
	public Page<ReservaDTO> findAll(Pageable pageable) {
		Page<Reserva> reservaPage = reservaService.findAllPage(pageable);
		List<ReservaDTO> reservaDtoList = reservaMapper.mapToDTO(reservaPage);
		return new PageImpl<ReservaDTO>(reservaDtoList, pageable, reservaPage.getTotalPages());
	}
	
	// Método que recibe por parámetro la id de la reserva que se quiera buscar
	@GetMapping("/{id}")
	public ReservaDTO findById(@PathVariable Long id) {
		Optional<Reserva> reserva = reservaService.findById(id);

		return reservaMapper.mapToDTO(reserva.get());
	}
	
	/* Método que recibe por parámetro el id del socio que va a realizar la reserva y el id del juego que va a reservar 
	 * para registrarlo en la base de datos
	 */
	@PostMapping("/socio/{idSocio}/juego/{idJuego}")
	public ReservaDTO create(@PathVariable Long idSocio, @PathVariable Long idJuego, @RequestBody ReservaDTO reservaDTO) {
		final Reserva reservaToCreate = reservaMapper.mapToModel(reservaDTO);
		final Reserva transaccionCreated = reservaService.create(reservaToCreate, idSocio, idJuego);
		
		return reservaMapper.mapToDTO(transaccionCreated);
	}
	
	// Método que recibe por parámetro la id de la reserva que necesite actualizar los datos
	@PutMapping("/{id}")
	public void update(@RequestBody ReservaDTO reservaDTO, @PathVariable Long id) {
		final Reserva reserva = reservaMapper.mapToModel(reservaDTO);
		reserva.setId(id);
		
		reservaService.update(reserva);
	}
	
	// Método que recibe por parámetro la id de la reserva que se haya cumplido de la base de datos
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		reservaService.delete(id);
	}

}