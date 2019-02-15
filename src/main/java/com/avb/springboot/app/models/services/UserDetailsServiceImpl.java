package com.avb.springboot.app.models.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avb.springboot.app.models.dao.SocioDAO;
import com.avb.springboot.app.models.dao.UsuarioDAO;
import com.avb.springboot.app.models.entity.Socio;
import com.avb.springboot.app.models.entity.Usuario;

/*Clase Service "UserDetailsServiceImpl" que implementa de la interface
 "UserDetailsService" e integra los métodos que tiene dicha interface */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	//Inyección de dependencia de la clase DAO "UsuarioDAO"
	@Autowired
	UsuarioDAO usuarioDao;
	
	//Inyección de dependencia de la clase DAO "SocioDAO"
	@Autowired
	SocioDAO socioDao;
	
	/* Método implementado de la interface "UserDetailsService" que carga un usuario por su nombre de usuario y devuelve un
	 * UserDetails, en este caso un nuevo JtwUser (ya que implementa esa interface)
	 */  
	@Override
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Usuario usuario = usuarioDao.findByUsername(username).orElseThrow(
				() -> new UsernameNotFoundException("Usuario no encontrado con el nombre de usuario: " + username));
		
		Socio socio = socioDao.findByUsuario(usuario);
		return JwtUser.create(socio);
	}
	

}
