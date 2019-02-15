package com.avb.springboot.app.controllers;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avb.springboot.app.dto.SocioDTO;
import com.avb.springboot.app.dto.UsuarioDTO;
import com.avb.springboot.app.mapper.SocioMapper;
import com.avb.springboot.app.messages.ResponseMessage;
import com.avb.springboot.app.models.dao.RolDAO;
import com.avb.springboot.app.models.dao.SocioDAO;
import com.avb.springboot.app.models.dao.UsuarioDAO;
import com.avb.springboot.app.models.entity.NombreRol;
import com.avb.springboot.app.models.entity.Rol;
import com.avb.springboot.app.models.entity.Socio;
import com.avb.springboot.app.security.jwt.JwtResponse;
import com.avb.springboot.app.security.jwt.JwtTokenUtil;

//@CrossOrigin es la anotación que comunica con la app de Angular en este caso
@CrossOrigin(origins = "*",  maxAge = 3600)
//@RestController es la anotación que crea un servicio REST
@RestController
/* @RequestMapping es la anotación que se encarga de relacionar un método con una petición http,
 * en este caso, todas las peticiones de este controlador empezarán con /api/auth.
 */
@RequestMapping("/api/auth")
public class AuthUsuarioController {

	// Inyección de dependencia de la clase AuthenticationManager
	@Autowired
	AuthenticationManager authenticationManager;
	
	// Inyección de dependencia de la clase DAO "UsuarioDAO"
	@Autowired
	UsuarioDAO usuarioDao;
	
	// Inyección de dependencia de la clase DAO "SocioDAO"
	@Autowired
	SocioDAO socioDao;
	
	// Inyección de dependencia de la clase DAO "RolDAO"
	@Autowired
	RolDAO rolDao;
	
	// Inyección de dependencia de la clase PasswordEncoder 
	@Autowired
	PasswordEncoder encoder;
	
	// Inyección de dependencia de la clase JwtTokenUtil
	@Autowired
	JwtTokenUtil jwtTokenUtil;
	
	// Inyección de dependencia de la clase Mapper "SocioMapper"
	@Autowired
	SocioMapper socioMapper;
	
	// Método para que inicie sesión un socio
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUsuarioSocio(@Valid @RequestBody UsuarioDTO usuarioDto) {
		
		// Objeto authentication que contiene el username y password del usuario que va a iniciar sesión
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(usuarioDto.getUsername(), usuarioDto.getPassword()));
		
		// A través de la clase SecurityContextHolder, se establece el objeto authentication
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		/* En el String jwt guardamos el valor del jwt, pasando por parámetro el objeto 
		 * authetication que se estableció y con ello, genera el jwt
		 */
		String jwt = jwtTokenUtil.generarJwtToken(authentication);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		
		//Devuelve un ResponseEntity con el status OK (200) y un objecto JwtResponse (token de acceso, username, roles autorizados)
		return ResponseEntity.ok(new JwtResponse(jwt,userDetails.getUsername(),userDetails.getAuthorities()));	
	}

	// Método para que se registre un socio
	@PostMapping("/signup")
	public ResponseEntity<?> registerUsuarioSocio(@Valid @RequestBody SocioDTO socioDto){
		
		/* Comprobación de si existe el username que haya elegido el socio en la base de datos, si es así devuelve un ResponseEntity 
		 * con el status BAD_REQUEST (400) y un mensaje de error
		 */
		if(usuarioDao.existsByUsername(socioDto.getUsuario().getUsername())) {
			return new ResponseEntity<>(new ResponseMessage("Fallo -> El nombre de usuario ya existe"), HttpStatus.BAD_REQUEST);
		}
		
		/* Comprobación de si existe el email que haya elegido el socio en la base de datos, si es así devuelve un ResponseEntity 
		 * con el status BAD_REQUEST (400) y un mensaje de error
		 */
		if(usuarioDao.existsByEmail(socioDto.getUsuario().getEmail())) {
			return new ResponseEntity<>(new ResponseMessage("Fallo -> El email ya existe"), HttpStatus.BAD_REQUEST);
		}
		
		/* A continuación se guarda en el String "encodeado", la contraseña que haya elegido el usuario y se encripta 
		 * con la función Bcrypt. Luego se establece esa contraseña encriptada al socio con todos los datos que 
		 * haya registrado y se crea en la base de datos.
		 */
		String encodeado = encoder.encode(socioDto.getUsuario().getPassword());
		socioDto.getUsuario().setPassword(encodeado);
		final Socio socioToCreate = socioMapper.mapToModel(socioDto);		

		
		/* Set de String que contiene los nombres de los roles autorizados que se hayan asignado a ese usuario.
		 * Set de Rol que contendrá Objetos Rol autorizados que se hayan asignado a ese usuario.
		 */
		Set<String> strRoles = socioDto.getRoles();
		Set<Rol> roles = new HashSet<>();
		
		
		/* Comprobación de cada nombre de rol autorizado, en caso de existir, se añade al Set de Rol, sino
		 * se lanza una excepción de que no existe ese rol
		 */
		strRoles.forEach(rol -> {
			switch (rol) {
			case "admin":
				Rol adminRol = rolDao.findByNombreRol(NombreRol.ROLE_ADMIN)
				.orElseThrow(() -> new RuntimeException("Fallo -> Causa: Rol de usuario no encontrado."));
			
			roles.add(adminRol);
			break;
			
			case "pm":
				Rol pmRol = rolDao.findByNombreRol(NombreRol.ROLE_PM)
				.orElseThrow(() -> new RuntimeException("Fallo -> Causa: Rol de usuario no encontrado."));
			
			roles.add(pmRol);
			break;
			
			default:
				Rol userRol = rolDao.findByNombreRol(NombreRol.ROLE_USER)
				.orElseThrow(() -> new RuntimeException("Fallo -> Causa: Rol de usuario no encontrado."));
			
			roles.add(userRol);
			}
		});
		
		/* Una vez comprobados y añadidos, se establecen esos roles al socio se guarda en la base de datos,
		 * y se devuelve un ResponseEntity con el nuevo Socio creado y el status OK (200) 
		 */
		socioToCreate.getUsuario().setRoles(roles);
		final Socio socioCreated = socioDao.save(socioToCreate);
		
		SocioDTO nuevoSocioDto = socioMapper.mapToDTO(socioCreated);
		
		return new ResponseEntity<SocioDTO>(nuevoSocioDto, HttpStatus.OK);
	}
}
