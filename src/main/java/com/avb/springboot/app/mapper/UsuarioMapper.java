package com.avb.springboot.app.mapper;

import com.avb.springboot.app.dto.UsuarioDTO;
import com.avb.springboot.app.models.entity.Usuario;

/*Interface "UsuarioMapper" que hereda de Mapper y se pasa el 
  Model/Entity "Usuario" y el DTO "UsuarioDTO"*/

public interface UsuarioMapper extends Mapper<Usuario, UsuarioDTO> {

}
