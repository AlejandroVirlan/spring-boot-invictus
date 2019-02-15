package com.avb.springboot.app.security.jwt;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

/* Clase "JWTResponse" que contiene:
 * - Token de autenticación
 * - Tipo de esquema del token
 * - Nombre de usuario
 * - Array de Autoridades del usuario
 *   
 *  Un constructor con todos los atributos menos el tipo de esquema del Token,
 *  y todos los getters y setters de los atributos.
 */

public class JwtResponse {
	
	private String token;
	private String type = "Bearer";
	private String username;
	private Collection<? extends GrantedAuthority> authorities;
	
	public JwtResponse(String accessToken, String username, Collection<? extends GrantedAuthority> authorities) {
		this.token = accessToken;
		this.username = username;
		this.authorities = authorities;
	}

	public String getAccessToken() {
		return token;
	}

	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}

	public String getTokenType() {
		return type;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
	
}
