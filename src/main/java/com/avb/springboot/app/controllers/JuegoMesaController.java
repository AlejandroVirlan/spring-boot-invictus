package com.avb.springboot.app.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avb.springboot.app.dto.JuegoMesaDTO;
import com.avb.springboot.app.mapper.JuegoMesaMapper;
import com.avb.springboot.app.models.entity.JuegoMesa;
import com.avb.springboot.app.models.services.JuegoMesaService;

//@CrossOrigin comunica con la app de Angular en este caso
@CrossOrigin(origins = "*")
//@RestController es la anotación que crea un servicio REST
@RestController
/* @RequestMapping es la anotación que se encarga de relacionar un método con una petición http,
 * en este caso, todas las peticiones de este controlador empezarán con /api/juegos.
 */
@RequestMapping("/api/juegos")
public class JuegoMesaController {

		// Inyección de dependencia de la clase Service "JuegoMesaService"
		@Autowired
		private JuegoMesaService juegoService;
		
		// Inyección de dependencia de la clase Mapper "JuegoMesaMapper"
		@Autowired
		JuegoMesaMapper juegoMapper;
		
		// Método que lista todos los juegos de la base de datos en un paginador
		@GetMapping
		public Page<JuegoMesaDTO> findAll(Pageable pageable) {
			Page<JuegoMesa> juegoPage = juegoService.findAllPage(pageable);
			List<JuegoMesaDTO> juegoDtoList = juegoMapper.mapToDTO(juegoPage);
			return new PageImpl<JuegoMesaDTO>(juegoDtoList, pageable, juegoPage.getTotalPages());
		}
		
		// Método que recibe por parámetro la id del juego que se quiera buscar
		@GetMapping("/{id}")
		public JuegoMesaDTO findById(@PathVariable Long id) {
			Optional<JuegoMesa> juego = juegoService.findById(id);
			
			return juegoMapper.mapToDTO(juego.get());
		}
		
		// Método para crear un juego en la base de datos
		@PostMapping
		public JuegoMesaDTO create(@RequestBody JuegoMesaDTO juegoDTO) {
			final JuegoMesa juegoToCreate = juegoMapper.mapToModel(juegoDTO);
			final JuegoMesa juegoCreated = juegoService.create(juegoToCreate);
			
			return juegoMapper.mapToDTO(juegoCreated);
		}
		
		// Método que recibe por parámetro la id del juego que se necesite actualizar los datos
		@PutMapping("/{id}")
		public void update(@RequestBody JuegoMesaDTO juegoDTO, @PathVariable Long id) {
			final JuegoMesa juego = juegoMapper.mapToModel(juegoDTO);
			juego.setId(id);
			juegoService.update(juego);
		}
		
		// Método que recibe por parámetro la id del juego que quiera marcarse como vendido en la base de datos
		@PutMapping("juego/{id}")
		public void delete(@RequestBody JuegoMesaDTO juegoMesaDTO, @PathVariable Long id) {
			final JuegoMesa juego = juegoMapper.mapToModel(juegoMesaDTO);
			juego.setId(id);
			juego.setVendido(true);
		    juegoService.update(juego);
		}
	}
