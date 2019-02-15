package com.avb.springboot.app.mapper;

import com.avb.springboot.app.dto.SocioDTO;
import com.avb.springboot.app.models.entity.Socio;

/*Interface "SocioMapper" que hereda de Mapper y se pasa el 
Model/Entity "Socio" y el DTO "SocioDTO"*/

public interface SocioMapper extends Mapper<Socio, SocioDTO> {

}
