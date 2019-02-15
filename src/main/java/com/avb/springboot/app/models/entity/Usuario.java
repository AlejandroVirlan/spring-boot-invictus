package com.avb.springboot.app.models.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Clase Entity "Usuario" con sus propiedades, restricciones y relaciones para definir la tabla "usuarios" en la base de datos

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="usuarios", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "username"
            }),
            @UniqueConstraint(columnNames = {
                "email"
            })
})

// Con esta anotación ignoro, estas dos propiedades, porque sino no serializa bien a JSON
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Usuario implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	@Column(length=30)
	private String username;
	
	@Column(length=60)
	private String password;
	
	@Email
	@NotEmpty
	private String email;
	
	private Boolean enabled;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="dd-MM-yyyy")
	private Date creado;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "roles_usuario",
		joinColumns = @JoinColumn(name="usuario_id"),
		inverseJoinColumns = @JoinColumn(name = "rol_id"))
	private Set<Rol> roles = new HashSet<>();
	
	 public Usuario(String username, String email, String password) {
	        this.username = username;
	        this.email = email;
	        this.password = password;
	 }
	
	@PrePersist
	public void prePersist() {
		creado = new Date();
		enabled = true;
	}
	
	private static final long serialVersionUID = 1L;

}
