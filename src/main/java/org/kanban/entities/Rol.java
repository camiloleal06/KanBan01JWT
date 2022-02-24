package org.kanban.entities;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.kanban.seguridad.modelo.RolEnum;

@Entity
public class Rol {
	@Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
 
	@Enumerated(EnumType.STRING)
	private RolEnum rol;
	public Rol() {

	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public RolEnum getRol() {
		return rol;
	}
	public void setRol(RolEnum rol) {
		this.rol = rol;
	}
	public Rol(RolEnum rol) {
		super();
		this.rol = rol;
	}
	
}
