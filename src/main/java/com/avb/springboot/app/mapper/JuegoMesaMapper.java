package com.avb.springboot.app.mapper;

import com.avb.springboot.app.dto.JuegoMesaDTO;
import com.avb.springboot.app.models.entity.JuegoMesa;

/*Interface "JuegoMesaMapper" que hereda de Mapper y se pasa el 
Model/Entity "JuegoMesa" y el DTO "JuegoMesaDTO"*/

public interface JuegoMesaMapper extends Mapper<JuegoMesa, JuegoMesaDTO>{

}
