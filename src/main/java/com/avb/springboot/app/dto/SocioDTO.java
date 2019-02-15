package com.avb.springboot.app.dto;

import java.util.Set;

import com.avb.springboot.app.models.entity.Usuario;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Builder;
import lombok.Data;

//Clase DTO "SocioDTO" donde se indica que valores
//se quieren establecer y obtener

//Gracias a la librería Lombok, con la anotación @Data 
//se implementa automáticamente, todos los getters y setters
//de los atributos, un constructor con todos los parámetros, los métodos
//toString(), hashCode(), equals(), canEquals()

//Con la anotación @Builder, implementamos el patrón Builder de forma efectiva en nuestro código, 
//creandonos así el método público build() y el objeto estático Builder con todos sus atributos y métodos

@Data
@Builder
public class SocioDTO {
	
	private Long id;
	private String dni;
	private String nombre;
	private String apellido1;
	private String apellido2;
	private String movil;
	private String foto;
	private Usuario usuario;
	//La anotación @JsonBackReference oculta los datos del atributo al que se le asigne en la respuesta JSON
	@JsonBackReference
	private Set<String> roles;
}
