package com.luisdbb.tarea3AD2024base.modelo;

import java.util.ArrayList;
import java.util.List;

public class Servicio {	
	private Long id;
	private String nombre;
	private double precio;
	 private List<Long> idParadas = new ArrayList<>();
	 private List<Long> conjuntoContratadoId= new ArrayList<>();

	
	
	
	

	 public Servicio(Long id, String nombre, double precio, List<Long> idParadas) {
	        this.id = id;
	        this.nombre = nombre;
	        this.precio = precio;
	        this.idParadas = idParadas;
	    }

	public Servicio() {
		super();
	}
	public List<Long> getConjuntoContratadoId() {
		return conjuntoContratadoId;
	}

	public void setConjuntoContratadoId(List<Long> conjuntoContratadoId) {
		this.conjuntoContratadoId = conjuntoContratadoId;
	}
	public List<Long> getIdParadas() {
        return idParadas;
    }

    public void setIdParadas(List<Long> idParadas) {
        this.idParadas = idParadas;
    }
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}

	
	

	@Override
	public String toString() {
		return "Servicio [id=" + id + ", nombre=" + nombre + ", precio=" + precio + ", idConjuntoContratado="
				 + "]";
	}
	
	
	
	
	
	
}
