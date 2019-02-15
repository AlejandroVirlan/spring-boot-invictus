package com.avb.springboot.app.models.services;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.avb.springboot.app.models.entity.Socio;
import com.avb.springboot.app.models.entity.Usuario;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter

/*
 * Clase "JwtUser" que implementa la interface "UserDetails" e integra los
 * métodos que tiene dicha interface
 */

/*
 * Con la anotación de @Getter implementamos directamente todos los getters de
 * los atributos de esta clase y con la anotación de @AllArgsConstructor implementamos 
 * automáticamente todos los atributos de la clase en el constructor de la clase
 */
public class JwtUser implements UserDetails {

	private static final long serialVersionUID = 1L;

	//Atributos que son necesarios para el login y registro
	private Long id;
	private String dni;
	private String nombre;
	private String apellido1;
	private String apellido2;
	private String movil;
	private String foto;
	private Usuario usuario;
	private Collection<? extends GrantedAuthority> authorities;

	//Método que crea y devuelve un nuevo JwtUser con los datos introducidos por el usuario
	public static JwtUser create(Socio socio) {

		List<GrantedAuthority> authorities = socio.getUsuario().getRoles().parallelStream()
				.map(rol -> new SimpleGrantedAuthority(rol.getNombreRol().name()))
				.collect(Collectors.toList());
		
		return new JwtUser(
				socio.getId(), 
				socio.getDni(), 
				socio.getNombre(), 
				socio.getApellido1(), 
				socio.getApellido2(),
				socio.getMovil(), 
				socio.getFoto(), 
				socio.getUsuario(), 
				authorities);
	}

	//Métodos implementados por la implementación de UserDetails
	
	// Método que obtiene la contraseña
	@Override
	public String getPassword() {
		return usuario.getPassword();
	}

	// Método que obtiene el nombre de usuario
	@Override
	public String getUsername() {
		return usuario.getUsername();
	}

	// Método que comprueba si la cuenta de usuario no está expirada
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	// Método que comprueba si la cuenta de usuario no está bloqueada
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	// Método que comprueba si las credenciales del usuario no están expiradas
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	// Método que comprueba si la cuenta de usuario está habilitada o activa
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	// Método que indica si algún otro objeto es "igual a" este.
	@Override
	public boolean equals(Object o) {
		
		if(this == o) {
			return true;
		}
		
		if(o == null || getClass() != o.getClass()) {
			return false;
		}
		
		JwtUser usuario = (JwtUser) o;
		return Objects.equals(id, usuario.id);
	}

}
