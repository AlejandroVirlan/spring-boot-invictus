package com.avb.springboot.app.mapper;

import com.avb.springboot.app.dto.ReservaDTO;
import com.avb.springboot.app.models.entity.Reserva;

/*Interface "ReservaMapper" que hereda de Mapper y se pasa el 
Model/Entity "Reserva" y el DTO "ReservaDTO"*/

public interface ReservaMapper extends Mapper<Reserva, ReservaDTO>{

}
