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

import com.avb.springboot.app.dto.UsuarioDTO;
import com.avb.springboot.app.mapper.UsuarioMapper;
import com.avb.springboot.app.models.entity.Usuario;
import com.avb.springboot.app.models.services.UsuarioService;

//@CrossOrigin comunica con la app de Angular en este caso
@CrossOrigin(origins={"http://localhost:4200"})
//@RestController es la anotación que crea un servicio REST
@RestController
/* @RequestMapping es la anotación que se encarga de relacionar un método con una petición http,
 * en este caso, todas las peticiones de este controlador empezarán con /api/usuarios.
 */
@RequestMapping("/api/usuarios")
public class UsuarioController {
	
	// Inyección de dependencia de la clase Service "UsuarioService"
	@Autowired
	private UsuarioService usuarioService;
	
	// Inyección de dependencia de la clase Mapper "UsuarioMapper"
	@Autowired
	UsuarioMapper usuarioMapper;
	
	// Método que lista todos los usuarios de la base de datos en un paginador
	@GetMapping
	public Page<UsuarioDTO> findAll(Pageable pageable) {
		Page<Usuario> usuarioPage = usuarioService.findAllPage(pageable);
		List<UsuarioDTO> usuarioDtoList = usuarioMapper.mapToDTO(usuarioPage); 
		return new PageImpl<UsuarioDTO>(usuarioDtoList, pageable, usuarioPage.getTotalPages());		
	}
	
	// Método que recibe por parámetro la id del usuario que se quiera buscar
	@GetMapping("/{id}")
	public UsuarioDTO findById(@PathVariable Long id) {
		Optional<Usuario> usuario = usuarioService.findById(id);
		return usuarioMapper.mapToDTO(usuario.get());
	}
	
	// Método para crear un usuario en la base de datos
	@PostMapping
	public UsuarioDTO create(@RequestBody UsuarioDTO usuarioDTO) {
		final Usuario usuarioToCreate = usuarioMapper.mapToModel(usuarioDTO);
		final Usuario usuarioCreated = usuarioService.create(usuarioToCreate);
		return usuarioMapper.mapToDTO(usuarioCreated);
	}
	
	// Método que recibe por parámetro la id del usuario que quiere actualizar sus datos
	@PutMapping("/{id}")
	public void update(@RequestBody UsuarioDTO usuarioDTO, @PathVariable Long id) {
		final Usuario usuario = usuarioMapper.mapToModel(usuarioDTO);
		usuario.setId(id);
		
		usuarioService.update(usuario);
	}
	
}
