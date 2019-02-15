package com.avb.springboot.app.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.avb.springboot.app.dto.ReservaDTO;
import com.avb.springboot.app.models.entity.Reserva;
import com.avb.springboot.app.models.services.ReservaService;

/* Clase Component "ReservaMapperImpl" que implementa la interface 
   "ReservaMapper" y por consecuencia todos los métodos que se 
   le ha definido a través de la interface "Mapper", para poder
   así implementar las distintas funcionalidades que emplea cada uno. */


@Component
public class ReservaMapperImpl implements ReservaMapper {

	@Autowired
	ReservaService service;
	
	/* Método que crea los valores de cada campo de un nuevo usuario 
	a tráves de los datos que recibe del DTO "ReservaDTO" por parámetro 
	o actualiza/modifica sus datos y devuelve un Modelo/Entity "Reserva" */
	@Override
	public Reserva mapToModel(ReservaDTO dto) {
		
		final Reserva reserva = new Reserva();
		
		reserva.setId(dto.getId());
		reserva.setFechaHora(dto.getFechaHora());
		return reserva;
	}

	/* Método que obtiene los valores de cada campo en el DTO 
	"ReservaDTO" a través del Modelo/Entity "Reserva" que se 
	pasa por parámetro y devuelve un DTO "ReservaDTO" */
	@Override
	public ReservaDTO mapToDTO(Reserva model) {
		return ReservaDTO.builder()
				.id(model.getId())
				.fechaHora(model.getFechaHora())
				.juego(model.getJuegoMesa().getNombre())
				.socio(model.getSocio().getNombre() + ' ' + model.getSocio().getApellido1() + ' ' + model.getSocio().getApellido2())
				.build();
	}
	
	/*Método que se le pasa por parámetro un List del Modelo/Entity "Reserva"
	y devuelve un List del DTO "ReservaDTO"*/
	@Override
	public List<ReservaDTO> mapToDTO(List<Reserva> models) {
		return models.parallelStream().map(this::mapToDTO)
				.collect(Collectors.toList());
	}

	/*Método que se le pasa por parámetro un Page del Modelo/Entity "Reserva"
	y devuelve un List del DTO "ReservaDTO"*/
	@Override
	public List<ReservaDTO> mapToDTO(Page<Reserva> models) {
		return models.getContent().parallelStream().map(this::mapToDTO)
                .collect(Collectors.toList());
	}

	

}
