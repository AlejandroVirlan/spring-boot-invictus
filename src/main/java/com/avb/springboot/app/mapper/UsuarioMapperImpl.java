package com.avb.springboot.app.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.avb.springboot.app.dto.UsuarioDTO;
import com.avb.springboot.app.models.entity.Usuario;

/* Clase Component "UsuarioMapperImpl" que implementa la interface 
   "UsuarioMapper" y por consecuencia todos los métodos que se 
   le ha definido a través de la interface "Mapper", para poder
   así implementar las distintas funcionalidades que emplea cada uno. */


@Component
public class UsuarioMapperImpl implements UsuarioMapper {
	
   /* Método que crea los valores de cada campo de un nuevo usuario 
	a tráves de los datos que recibe del DTO "UsuarioDTO" por parámetro 
	o actualiza/modifica sus datos y devuelve un Modelo/Entity "Usuario" */
	@Override
	public Usuario mapToModel(UsuarioDTO dto) {
		
		final Usuario usuario = new Usuario();
		
		usuario.setId(dto.getId());
		usuario.setUsername(dto.getUsername());
		usuario.setPassword(dto.getPassword());
		usuario.setEmail(dto.getEmail());
		usuario.setEnabled(dto.getEnabled());
		usuario.setCreado(dto.getCreado());
		return usuario;
	}

	/* Método que obtiene los valores de cada campo en el DTO 
	"UsuarioDTO" a través del Modelo/Entity "Usuario" que se 
	pasa por parámetro y devuelve un DTO "UsuarioDTO" */
	@Override
	public UsuarioDTO mapToDTO(Usuario model) {
		return UsuarioDTO.builder()
				.id(model.getId())
				.username(model.getUsername())
				.password(model.getPassword())
				.email(model.getEmail())
				.enabled(model.getEnabled())
				.creado(model.getCreado())
				.build();
	}

	
	//(:: expresion lambda)
	//lanza un conjunto de hilos para realizar la tarea de
	//transformar un Stream de Modelo/Entity a un Stream de DTO
	//y los añade a un List
	

	/*Método que se le pasa por parámetro un Page del Modelo/Entity "Usuario"
	y devuelve un List del DTO "UsuarioDTO" junto a un paginador*/
	@Override
	public List<UsuarioDTO> mapToDTO(Page<Usuario> models) {
		return models.getContent().parallelStream().map(this::mapToDTO)
                .collect(Collectors.toList());
	}

	@Override
	public List<UsuarioDTO> mapToDTO(List<Usuario> models) {
		return models.parallelStream().map(this::mapToDTO)
				.collect(Collectors.toList());
	}

}
