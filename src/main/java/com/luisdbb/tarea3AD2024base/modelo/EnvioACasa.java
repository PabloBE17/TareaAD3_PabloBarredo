package com.luisdbb.tarea3AD2024base.modelo;

import jakarta.persistence.Embedded;

public class EnvioACasa {
	private long id;
	private double peso;
	private int volumen;
	private boolean urgente=false;
	@Embedded
	Direccion direccion;	
	private long idParada;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public double getPeso() {
		return peso;
	}
	public void setPeso(double peso) {
		this.peso = peso;
	}
	public int getVolumen() {
		return volumen;
	}
	public void setVolumen(int volumen) {
		this.volumen = volumen;
	}
	public boolean isUrgente() {
		return urgente;
	}
	public void setUrgente(boolean urgente) {
		this.urgente = urgente;
	}
	public Direccion getDireccion() {
		return direccion;
	}
	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}
	public long getIdParada() {
		return idParada;
	}
	public void setIdParada(long idParada) {
		this.idParada = idParada;
	}
	@Override
	public String toString() {
		return "EnvioACasa [id=" + id + ", peso=" + peso + ", volumen=" + volumen + ", urgente=" + urgente + "]";
	}
	
}
