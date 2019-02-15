package com.avb.springboot.app.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.avb.springboot.app.dto.SocioDTO;
import com.avb.springboot.app.models.entity.Socio;

/* Clase Component "SocioMapperImpl" que implementa la interface 
   "SocioMapper" y por consecuencia todos los métodos que se 
   le ha definido a través de la interface "Mapper", para poder
   así implementar las distintas funcionalidades que emplea cada uno. */

@Component
public class SocioMapperImpl implements SocioMapper {
	
	/* Método que crea los valores de cada campo de un nuevo usuario 
	a tráves de los datos que recibe del DTO "SocioDTO" por parámetro 
	o actualiza/modifica sus datos y devuelve un Modelo/Entity "Socio" */
	@Override
	public Socio mapToModel(SocioDTO dto) {
		final Socio socio = new Socio();
		
		socio.setId(dto.getId());
		socio.setDni(dto.getDni());
		socio.setNombre(dto.getNombre());
		socio.setApellido1(dto.getApellido1());
		socio.setApellido2(dto.getApellido2());
		socio.setMovil(dto.getMovil());
		socio.setFoto(dto.getFoto());
		socio.setUsuario(dto.getUsuario());
		
		return socio;
	}

	/* Método que obtiene los valores de cada campo en el DTO 
	"SocioDTO" a través del Modelo/Entity "Socio" que se 
	pasa por parámetro y devuelve un DTO "SocioDTO" */
	@Override
	public SocioDTO mapToDTO(Socio model) {
		return SocioDTO.builder()
				.id(model.getId())
				.dni(model.getDni())
				.nombre(model.getNombre())
				.apellido1(model.getApellido1())
				.apellido2(model.getApellido2())
				.movil(model.getMovil())
				.foto(model.getFoto())
				.usuario(model.getUsuario())
				.build();
	}
	
	/*Método que se le pasa por parámetro un List del Modelo/Entity "Socio"
	y devuelve un List del DTO "SocioDTO"*/
	@Override
	public List<SocioDTO> mapToDTO(List<Socio> models) {
		return models.parallelStream().map(this::mapToDTO)
				.collect(Collectors.toList());
	}

	/*Método que se le pasa por parámetro un Page del Modelo/Entity "Socio"
	y devuelve un List del DTO "SocioDTO"*/
	@Override
	public List<SocioDTO> mapToDTO(Page<Socio> models) {
		return models.getContent().parallelStream().map(this::mapToDTO)
                .collect(Collectors.toList());
	}

	

}
