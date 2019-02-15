package com.avb.springboot.app.security.jwt;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.avb.springboot.app.models.services.JwtUser;
//import com.avb.springboot.app.models.services.UsuarioPrincipal;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

/*
 * Clase Component "JwtTokenUtil" que contiene los valores necesarios para un Jwt 
 * y los métodos para generar y validar un Jwt y obtener el usuario a partir de este
 */

@Component
public class JwtTokenUtil {
	
		private static final Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);
		
		//La anotación @Value indica una expresión de valor por defecto.
		
		/* Inyecta el valor de la propiedad "invictus.app.jwtSecret" en el atributo String "jwtSecret"
		 * que representa el valor de secreto del jwt
		 */
		@Value("${invictus.app.jwtSecret}")
		private String jwtSecret;
		
		/* Inyecta el valor de la propiedad "invictus.app.jwtExpiration" en el atributo int "jwtExpiration"
		 * que representa el tiempo de expirar el jwt
		 */
		@Value("${invictus.app.jwtExpiration}")
		private int jwtExpiration;
		
		/* Método que genera un Jwt a través de la autenticación que recibe. Una vez que obtiene el usuario
		 * por la autenticación, devuelve un Jwt con todos los datos necesarios
		 */
		public String generarJwtToken(Authentication authentication) {
			
			JwtUser usuarioPrincipal = (JwtUser) authentication.getPrincipal();
			
			return Jwts.builder()
					.setSubject((usuarioPrincipal.getUsername()))
					.setIssuedAt(new Date())
					.setExpiration(new Date((new Date()).getTime() + jwtExpiration*1000))
					.signWith(SignatureAlgorithm.HS512, jwtSecret)
					.compact();
		}
		
		/* Método que controla la validación de jwt, recibiendo el token por parámetro que devuelve un boolean
		 * y un mensaje de error sino es válido
		 */
		public boolean validarJwtToken(String authToken) {
			
			try {
				Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
				return true;
			} catch (SignatureException e) {
				logger.error("Firma inválida JWT -> Mensaje: {} ", e);
			} catch (MalformedJwtException e) {
				logger.error("Token JWT inválido -> Mensaje: {} ", e);
			} catch (ExpiredJwtException e) {
				logger.error("Token JWT expirado -> Mensaje: {} ", e);
			} catch (UnsupportedJwtException e) {
				logger.error("Token JWT no soportado -> Mensaje: {} ", e);
			} catch (IllegalArgumentException e) {
				logger.error("String de reclamaciones JWT está vacío -> Mensaje: {} ", e);
			}
			
			return false;	
		}
		
		// Método que devuelve el nombre de usuario obtenido del Jwt
		public String getUsernameFromJwtToken(String token) {
			return Jwts.parser().setSigningKey(jwtSecret)
					.parseClaimsJws(token)
					.getBody().getSubject();
		}
	}

