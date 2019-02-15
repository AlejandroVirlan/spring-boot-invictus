package com.avb.springboot.app.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

//Clase para informar de los mensajes de error 

@Getter
@Setter
@AllArgsConstructor
public class ResponseMessage {
	
	private String mensaje;
	
}
