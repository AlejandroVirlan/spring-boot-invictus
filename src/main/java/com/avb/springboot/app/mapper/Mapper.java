package com.avb.springboot.app.mapper;

import java.util.List;

import org.springframework.data.domain.Page;

/* Interface "Mapper" al que se le pasa el Modelo/Entity y DTO,
 con la definición de métodos para comunicar el DTO con Modelo/Entity
 y viceversa*/

public interface Mapper<M, DTO> {
	
	M mapToModel(DTO dto);
	
	DTO mapToDTO(M Object);
		
	List<DTO> mapToDTO(Page<M> pageModels);
	
	List<DTO> mapToDTO(List<M> models);
}
