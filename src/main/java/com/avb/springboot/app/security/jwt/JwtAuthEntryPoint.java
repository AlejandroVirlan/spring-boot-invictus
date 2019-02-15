package com.avb.springboot.app.security.jwt;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/* Clase Component "JwtAuthEntryPoint" que implementa de la interface
 * "AuthenticationEntryPoint" e integra los métodos que tiene dicha interface
 */ 

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {

	private static final Logger logger = LoggerFactory.getLogger(JwtAuthEntryPoint.class);
	
	/* Si el usuario solicita un recurso HTTP seguro sin ser autenticado, 
	 * lanza el método commence() para mandar la respuesta con el error 
	 * de que esa petición no está autorizada.
	 */
	@Override
	public void commence(HttpServletRequest request, 
						 HttpServletResponse response,
						 AuthenticationException authException) throws IOException, ServletException {
		
		logger.error("Error No autorizado. Mensaje - {}", authException.getMessage());
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error -> No autorizado");
	}
}
