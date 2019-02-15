package com.avb.springboot.app.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.avb.springboot.app.dto.JuegoMesaDTO;
import com.avb.springboot.app.models.entity.JuegoMesa;
import com.avb.springboot.app.models.services.JuegoMesaService;
import com.github.slugify.Slugify;

/* Clase Component "JuegoMesaImpl" que implementa la interface 
   "JuegoMapper" y por consecuencia todos los métodos que se 
   le ha definido a través de la interface "Mapper", para poder
   así implementar las distintas funcionalidades que emplea cada uno. */

@Component
public class JuegoMesaMapperImpl implements JuegoMesaMapper {

	@Autowired
	JuegoMesaService service;
	
	/* Método que crea los valores de cada campo de un nuevo juego de mesa 
	a tráves de los datos que recibe del DTO "JuegoMesaDTO" por parámetro 
	o actualiza/modifica sus datos y devuelve un Modelo/Entity "JuegoMesa" */
	@Override
	public JuegoMesa mapToModel(JuegoMesaDTO dto) {
		final JuegoMesa juego = new JuegoMesa();
		
		Slugify slg = new Slugify();
		
		juego.setId(dto.getId());
		juego.setNombre(dto.getNombre());
		juego.setResumen(dto.getResumen());
		juego.setPrecio(dto.getPrecio());
		juego.setVendido(dto.getVendido());
		juego.setVideo(dto.getVideo());
		juego.setSlug(slg.slugify(dto.getSlug()));
		
		return juego;	
	}

	/* Método que obtiene los valores de cada campo en el DTO 
	"JuegoMesaDTO" a través del Modelo/Entity "JuegoMesa" que se 
	pasa por parámetro y devuelve un DTO "JuegoMesaDTO" */
	@Override
	public JuegoMesaDTO mapToDTO(JuegoMesa model) {
		return JuegoMesaDTO.builder()
				.id(model.getId())
				.nombre(model.getNombre())
				.resumen(model.getResumen())
				.precio(model.getPrecio())
				.vendido(model.getVendido())
				.video(model.getVideo())
				.slug(model.getSlug())
				.build();
	}
	
	/*Método que se le pasa por parámetro un List del Modelo/Entity "JuegoMesa"
	y devuelve un List del DTO "JuegoMesaDTO"*/
	@Override
	public List<JuegoMesaDTO> mapToDTO(List<JuegoMesa> models) {
		return models.parallelStream().map(this::mapToDTO)
				.collect(Collectors.toList());
	}
	
	/*Método que se le pasa por parámetro un Page del Modelo/Entity "JuegoMesa"
	y devuelve un List del DTO "JuegoMesaDTO"*/
	@Override
	public List<JuegoMesaDTO> mapToDTO(Page<JuegoMesa> models) {
		return models.getContent().parallelStream().map(this::mapToDTO)
                .collect(Collectors.toList());
	}

	

}
