package com.avb.springboot.app.mapper;

import com.avb.springboot.app.dto.TransaccionDTO;
import com.avb.springboot.app.models.entity.Transaccion;

/*Interface "TransaccionMapper" que hereda de Mapper y se pasa el 
Model/Entity "Transaccion" y el DTO "TransaccionDTO"*/

public interface TransaccionMapper extends Mapper<Transaccion, TransaccionDTO> {

}
