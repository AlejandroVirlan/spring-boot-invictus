package com.avb.springboot.app.controllers;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avb.springboot.app.dto.SocioDTO;
import com.avb.springboot.app.mapper.SocioMapper;
import com.avb.springboot.app.models.entity.Socio;
import com.avb.springboot.app.models.services.SocioService;

//@CrossOrigin comunica con la app de Angular en este caso
@CrossOrigin(origins={"http://localhost:4200"})
//@RestController es la anotación que crea un servicio REST
@RestController
/* @RequestMapping es la anotación que se encarga de relacionar un método con una petición http,
 * en este caso, todas las peticiones de este controlador empezarán con /api/usuarios.
 */
@RequestMapping("/api/socios")
public class SocioController {
	
	// Inyección de dependencia de la clase Service "SocioService"
	@Autowired
	private SocioService socioService;
	
	// Inyección de dependencia de la clase Mapper "SocioMapper"
	@Autowired
	SocioMapper socioMapper;
	
	// La anotación @PreAuthorize determina que roles tienen permitido el acceso a esa URL 

	// Método que lista todos los socios de la base de datos en un paginador
	@GetMapping
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public Page<SocioDTO> findAll(Pageable pageable) {
		Page<Socio> socioPage = socioService.findAllPage(pageable);
		List<SocioDTO> socioDtoList = socioMapper.mapToDTO(socioPage);
		return new PageImpl<SocioDTO>(socioDtoList, pageable, socioPage.getTotalPages());
	}
	
	// Método que recibe por parámetro la id del socio que se quiera buscar
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public SocioDTO findById(@PathVariable Long id) {
		Optional<Socio> socio = socioService.findById(id);
		return socioMapper.mapToDTO(socio.get());
	}
	
	// Método para crear un socio en la base de datos
	@PostMapping
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public SocioDTO create(@RequestBody SocioDTO socioDTO) {
		final Socio socioToCreate = socioMapper.mapToModel(socioDTO);
		final Socio socioCreated = socioService.create(socioToCreate);
		return socioMapper.mapToDTO(socioCreated);
	}
	
	// Método que recibe por parámetro la id del socio que quiere actualizar sus datos
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public void update(@RequestBody SocioDTO socioDTO, @PathVariable Long id) {
		
		/* No se puede modificar los datos de la tabla usuario a no ser que haga el Cascade.ALL 
		 * en el objeto Usuario del Entity Socio
		 */
		
		final Socio socio = socioMapper.mapToModel(socioDTO);
		Date creado = new Date();
		socio.setId(id);
		socio.getUsuario().setId(id);
		socio.getUsuario().setEnabled(true);
		socio.getUsuario().setCreado(creado);
		socioService.update(socio);
	}
	
	// Método que recibe por parámetro la id del socio que se va a dar de baja
	@PutMapping("socio/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public void delete(@RequestBody SocioDTO socioDTO, @PathVariable Long id) {
		final Socio socio = socioMapper.mapToModel(socioDTO);
		socio.setId(id);
		socio.getUsuario().setId(id);
		socio.getUsuario().setEnabled(false);
		socioService.update(socio);
	}
}
