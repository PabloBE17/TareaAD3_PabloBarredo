package com.luisdbb.tarea3AD2024base.modelo;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
@Embeddable
public class Direccion implements Serializable{
private long idDireccion;
private String direccion;
private String localidad;


public long getId() {
	return idDireccion;
}
public void setId(long id) {
	this.idDireccion = id;
}
public String getDireccion() {
	return direccion;
}
public void setDireccion(String direccion) {
	this.direccion = direccion;
}
public String getLocalidad() {
	return localidad;
}
public void setLocalidad(String localidad) {
	this.localidad = localidad;
}
@Override
public String toString() {
	return "Direccion [id=" + idDireccion + ", direccion=" + direccion + ", localidad=" + localidad + "]";
}


}
