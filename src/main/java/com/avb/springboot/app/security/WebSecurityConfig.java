package com.avb.springboot.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.avb.springboot.app.models.services.UserDetailsServiceImpl;
import com.avb.springboot.app.security.jwt.JwtAuthEntryPoint;
import com.avb.springboot.app.security.jwt.JwtAuthTokenFilter;

/*
 * Clase "WebSecurityConfig" que hereda de la clase "WebSecurityConfigurerAdapter" para 
 * poder utilizar ciertos métodos para poder configurar la seguridad de la API y la web.
 */

/*
 * Con la anotación de @Configuration indicamos que esta clase declara uno o más métodos
 * que se ha anotado con @Bean para que puedan ser procesados por el contenedor de Spring 
 * para que pueda así generar definiciones de lo que contenga la anotación @Bean y 
 * solicitudes de servicio para estos en tiempo de ejecución.
 * 
 * La anotación @EnableWebSecurity se agrega con una clase anotada con @Configuration para 
 * poder tener la configuración de Spring Security en esta clase "WebSecurityConfigurer y 
 * poder así sobreescribir los métodos necesarios.
 * 
 * @EnableGlobalMethodSecurity es una anotación necesaria para que pueda funcionar la 
 * anotación @PreAuthorize a la que va ligada, ya que esta última anotación especifica
 * el control de acceso al método que se evaluará para decidir si permite o no una 
 * invocación del método.
 * 
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	// Inyección de dependencia de la clase "UserDetailsServiceImpl"
	@Autowired
	UserDetailsServiceImpl userDetailsService;
	
	// Inyección de dependencia de la clase "JwtAuthEntryPoint" 
	@Autowired
	private JwtAuthEntryPoint unauthorizedHandler;
	
	/* 
	 * Método polimórfico que se encarga de filtrar las peticiones que realizan los usuarios 
	 * (autenticaciones, permisos, autorizaciones para ciertas URL, recursos permitidos,
	 * sesiones)
	 */
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable()
		.authorizeRequests()
		.antMatchers("/api/auth/**").permitAll()
		.anyRequest().authenticated()
		.and()
		.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

	}
	
	/* La anotación @Bean indica que el método que sea anotado, produce un componente 
	 * para que sea manejado y se añada al contenedor de Spring
	 */
	
	/*
	 * Método creado para filtrar la autenticación del Jwt, gracias a la inyección
	 * de la clase "JwtAuthTokenFilter" devolviendo así un nuevo objeto de esta clase
	 */

	@Bean
	public JwtAuthTokenFilter authenticationJwtTokenFilter() {
		return new JwtAuthTokenFilter();	
	}
	
	/*
	 * Método polimórfico que accede a los detalles del usuario para poder encriptar
	 * su contraseña gracias al método passwordEncoder()
	 */
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService)
			.passwordEncoder(passwordEncoder());
	}
	
    // Método que procesa y gestiona las peticiones con autenticación
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	// Método que encripta las contraseñas que introducen los usuarios 
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
