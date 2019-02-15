package com.avb.springboot.app.controllers;

import java.util.Date;
import java.util.List;
import java.util.Map;
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

import com.avb.springboot.app.dto.TransaccionDTO;
import com.avb.springboot.app.mapper.TransaccionMapper;
import com.avb.springboot.app.models.entity.Transaccion;
import com.avb.springboot.app.models.services.TransaccionService;

//@CrossOrigin es la anotación que comunica con la app de Angular en este caso
@CrossOrigin(origins = "*")
//@RestController es la anotación que crea un servicio REST
@RestController
/* @RequestMapping es la anotación que se encarga de relacionar un método con una petición http,
 * en este caso, todas las peticiones de este controlador empezarán con /api/transacciones.
 */
@RequestMapping("/api/transacciones")
public class TransaccionController {

	// Inyección de dependencia de la clase Service "TransaccionService"
	@Autowired
	private TransaccionService transaccionService;
	
	// Inyección de dependencia de la clase Mapper "TransaccionMapper"
	@Autowired
	TransaccionMapper transaccionMapper;

	// Método que lista todas las transsaciones de la base de datos en un paginador
	@GetMapping
	public Page<TransaccionDTO> findAll(Pageable pageable) {
		Page<Transaccion> transaccionPage = transaccionService.findAllPage(pageable);
		List<TransaccionDTO> transaccionDtoList = transaccionMapper.mapToDTO(transaccionPage);
		return new PageImpl<TransaccionDTO>(transaccionDtoList, pageable, transaccionPage.getTotalPages());
	}
	
	// Método que recibe por parámetro la id de la transacción que se quiera buscar
	@GetMapping("/{id}")
	public TransaccionDTO findById(@PathVariable Long id) {
		Optional<Transaccion> transaccion = transaccionService.findById(id);

		return transaccionMapper.mapToDTO(transaccion.get());
	}

	// Método que recibe por parámetro el id del socio que va a realizar la transaccion para registrarla en la base de datos
	@PostMapping("/socio/{idSocio}")
	public TransaccionDTO create(@PathVariable Long idSocio, @RequestBody TransaccionDTO transaccionDTO) {
		final Transaccion transaccionToCreate = transaccionMapper.mapToModel(transaccionDTO);
		final Transaccion transaccionCreated = transaccionService.create(transaccionToCreate, idSocio);
		
		return transaccionMapper.mapToDTO(transaccionCreated);
	}
	
	// Método que recibe por parámetro la id de la transacción que necesite actualizar sus datos
	@PutMapping("/{id}")
	public void update(@RequestBody TransaccionDTO transaccionDTO, @PathVariable Long id) {
		final Transaccion transaccion = transaccionMapper.mapToModel(transaccionDTO);
		transaccion.setId(id);
		
		transaccionService.update(transaccion);
	}
	
	// Método que recibe por parámetro la id de la transacción que quiera eliminarse de la base de datos
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		transaccionService.delete(id);
	}
		
	// Método que lista las transacciones que haya registradas entre dos fechas 
	@GetMapping("/fechas")
	public List<TransaccionDTO> findFechas(@RequestBody Map<String, Date> fechas) {	
		Date fechaInicio = fechas.get("fechaInicio");
		Date fechaFin = fechas.get("fechaFin");
		
		final List<Transaccion> transacciones = transaccionService.encontrarFacturasEntreFechas(fechaInicio, fechaFin);
		return transaccionMapper.mapToDTO(transacciones);
	}
}
