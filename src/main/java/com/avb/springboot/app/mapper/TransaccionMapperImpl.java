package com.avb.springboot.app.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.avb.springboot.app.dto.TransaccionDTO;
import com.avb.springboot.app.models.entity.Transaccion;
import com.avb.springboot.app.models.services.TransaccionService;

/* Clase Component "TransaccionMapperImpl" que implementa la interface 
   "TransaccionMapper" y por consecuencia todos los métodos que se 
   le ha definido a través de la interface "Mapper", para poder
   así implementar las distintas funcionalidades que emplea cada uno. */

@Component
public class TransaccionMapperImpl implements TransaccionMapper {

	@Autowired
	TransaccionService service;
	
	/* Método que crea los valores de cada campo de un nuevo usuario 
	a tráves de los datos que recibe del DTO "TransaccionDTO" por parámetro 
	o actualiza/modifica sus datos y devuelve un Modelo/Entity "TransaccionDTO" */
	@Override
	public Transaccion mapToModel(TransaccionDTO dto) {
		final Transaccion transaccion = new Transaccion();
		
		transaccion.setId(dto.getId());
		transaccion.setConcepto(dto.getConcepto());
		transaccion.setImporte(dto.getImporte());
		transaccion.setFecha(dto.getFechaIngreso());
		transaccion.setEscaneoFactura(dto.getEscaneoFactura());
		
		return transaccion;
		
	}

	/* Método que obtiene los valores de cada campo en el DTO 
	"TransaccionDTO" a través del Modelo/Entity "Transaccion" que se 
	pasa por parámetro y devuelve un DTO "TransaccionDTO" */
	@Override
	public TransaccionDTO mapToDTO(Transaccion model) {
		return TransaccionDTO.builder()
				.id(model.getId())
				.concepto(model.getConcepto())
				.importe(model.getImporte())
				.fechaIngreso(model.getFecha())
				.escaneoFactura(model.getEscaneoFactura())
				.socio(model.getSocio().getNombre() + ' ' + model.getSocio().getApellido1() + ' ' + model.getSocio().getApellido2())
				.build();
	}
	
	/*Método que se le pasa por parámetro un List del Modelo/Entity "Transaccion"
	y devuelve un List del DTO "TransaccionDTO"*/
	@Override
	public List<TransaccionDTO> mapToDTO(List<Transaccion> models) {
		return models.parallelStream().map(this::mapToDTO)
				.collect(Collectors.toList());
	}

	/*Método que se le pasa por parámetro un Page del Modelo/Entity "Transaccion"
	y devuelve un List del DTO "TransaccionDTO"*/
	@Override
	public List<TransaccionDTO> mapToDTO(Page<Transaccion> models) {
		return models.getContent().parallelStream().map(this::mapToDTO)
                .collect(Collectors.toList());
	}
}
