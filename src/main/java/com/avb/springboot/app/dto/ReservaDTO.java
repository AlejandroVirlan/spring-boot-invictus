package com.avb.springboot.app.dto;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

//Clase DTO "ReservaDTO" donde se indica que valores
//se quieren establecer y obtener

//Gracias a la librería Lombok, con la anotación @Data 
//se implementa automáticamente, todos los getters y setters
//de los atributos, un constructor con todos los parámetros, los métodos
//toString(), hashCode(), equals(), canEquals()

//Con la anotación @Builder, implementamos el patrón Builder de forma efectiva en nuestro código, 
//creandonos así el método público build() y el objeto estático Builder con todos sus atributos y métodos

@Data
@Builder
public class ReservaDTO {
	
	private Long id;
	private Date fechaHora;
	private String juego;
	private String socio;
	
}
