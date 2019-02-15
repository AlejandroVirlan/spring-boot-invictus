package com.avb.springboot.app.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.avb.springboot.app.models.services.UserDetailsServiceImpl;

//Clase "JwtAuthTokenFilter" que hereda de la clase "OncePerRequestFilter"

public class JwtAuthTokenFilter extends OncePerRequestFilter {

	//Inyección de dependencia de la clase JwtTokenUtil
	@Autowired
	private JwtTokenUtil tokenUtil;

	//Inyección de dependencia de la clase UserDetailsServiceImpl
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	private static final Logger logger = LoggerFactory.getLogger(JwtAuthTokenFilter.class);
	
	
	/* Método heredado y sobreescrito de la clase "OncePerRequestFilter"
	 * 
	 * Pasa como parámetros una petición, una respuesta y una cadena de filtros.
	 * Dentro de un try-catch, se guarda en un String el "Json Web Token" (jwt)
	 * y se comprueba si es distinto de nulo y si es válido.
	 * 
	 * Después de la comprobación, obtiene el nombre de usuario (username) y 
	 * los detalles de este usuario (userDetails) para poder crear así
	 * un objeto "UsernamePasswordAuthenticationToken" y poder obtener los roles
	 * autorizados y establecer los detalles y autenticación del usuario.
	 * 
	 * Sino es así, lanzará la excepción de que no se ha podido establecer 
	 * la autenticación del usuario.
	 * 
	 * Por último con la cadena de filtros, se filtra la petición y la respuesta.
	 * 
	 */
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		try {
			String jwt = getJwt(request);
			if(jwt != null && tokenUtil.validarJwtToken(jwt)) {
				
				String username = tokenUtil.getUsernameFromJwtToken(jwt);
				
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} catch (Exception e) {
			logger.error("No puede establecer la autenticación del usuario -> Mensaje: {}", e);
		}
		
		filterChain.doFilter(request, response);
	}
	
	
	//Método que crea el JWT para que después pueda obtenerse
	private String getJwt(HttpServletRequest request) {
		
		//Obtiene la cabecera de la petición con la autorización
		String authHeader = request.getHeader("Authorization");
		
		 
		 /* Comprueba si la cabecera no es nula y si empieza por 
		  * "Bearer " (tipo de esquema para el token),
		  * 
		  * Si es así, reemplaza todas las ocurrencias que ponga 
		  * Bearer y un espacio por una cadena vacía y lo devuelve, 
		  * sino, devuelve nulo
		  * 
		  */
		if(authHeader != null && authHeader.startsWith("Bearer ")) {
			return authHeader.replace("Bearer ", "");
		}
		
		return null;
	}

}
