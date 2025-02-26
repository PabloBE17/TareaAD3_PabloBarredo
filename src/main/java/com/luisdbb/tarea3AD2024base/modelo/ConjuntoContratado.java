package com.luisdbb.tarea3AD2024base.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ConjuntoContratado implements Serializable {
	private long id;
	private double precioTotal;
	private char modoPago;
	private String extra=null;
	private long idEstancia;	
	private List<Long> idServivcio= new ArrayList<>(); 	
	
	
    
	
	public ConjuntoContratado(long id, double precioTotal, char modoPago, String extra, long idEstancia,
			List<Long> idServivcio) {
		super();
		this.id = id;
		this.precioTotal = precioTotal;
		this.modoPago = modoPago;
		this.extra = extra;
		this.idEstancia = idEstancia;
		this.idServivcio = idServivcio;
	}
	
	public ConjuntoContratado() {
		super();
	}

	public List<Long> getIdServivcio() {
		return idServivcio;
	}
	public void setIdServivcio(List<Long> idServivcio) {
		this.idServivcio = idServivcio;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public double getPrecioTotal() {
		return precioTotal;
	}
	public void setPrecioTotal(double precioTotal) {
		this.precioTotal = precioTotal;
	}
	public char getModoPago() {
		return modoPago;
	}
	public void setModoPago(char modoPago) {
		this.modoPago = modoPago;
	}
	public String getExtra() {
		return extra;
	}
	public void setExtra(String extra) {
		this.extra = extra;
	}
	public long getIdEstancia() {
		return idEstancia;
	}
	public void setIdEstancia(long idEstancia) {
		this.idEstancia = idEstancia;
	}
	

	
	@Override
	public String toString() {
		return "ConjuntoContratado [id=" + id + ", precioTotal=" + precioTotal + ", modoPago=" + modoPago + ", extra="
				+ extra + "]";
	}
	
	
}
