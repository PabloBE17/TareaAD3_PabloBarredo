package com.luisdbb.tarea3AD2024base.modelo;

import java.time.LocalDate;

import jakarta.persistence.*;
@Entity
@Table(name = "Carnet")
public class Carnet {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private long id;
	@Column(name = "fechaExpedicion")
    private LocalDate fechaExpedicion;
	@Column(name = "distancia")
    private double distancia=0.0;
	@Column(name = "numVips")
    private int numVips=0;
	@ManyToOne
	@JoinColumn(name = "parada_inicial_id") 
	private Parada paradaInicial;
    
    

    public Carnet(long id, Parada paradaInicial) {
    	 
         this.fechaExpedicion = LocalDate.now();
         this.paradaInicial = paradaInicial;
    }
    public Carnet(long id, LocalDate fechaExpedicion, double distancia, int numVips, Parada paradaInicial) {
        
        this.fechaExpedicion = fechaExpedicion;
        this.distancia = distancia;
        this.numVips = numVips;
        this.paradaInicial = paradaInicial;
    }

    
    public Carnet() {
		super();
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public LocalDate getFechaExpedicion() {
		return fechaExpedicion;
	}


	public void setFechaExpedicion(LocalDate fechaExpedicion) {
		this.fechaExpedicion = fechaExpedicion;
	}


	public double getDistancia() {
		return distancia;
	}


	public void setDistancia(double distancia) {
		this.distancia = distancia;
	}


	public int getNumVips() {
		return numVips;
	}


	public void setNumVips(int numVips) {
		this.numVips = numVips;
	}


	public Parada getParadaInicial() {
		return paradaInicial;
	}


	public void setParadaInicial(Parada paradaInicial) {
		this.paradaInicial = paradaInicial;
	}


	 @Override
	    public String toString() {
	        return "Carnet{" + "id=" + id + ", fechaExpedicion=" + fechaExpedicion + ", distancia=" + distancia + ", numVips=" + numVips + '}';
	    }
	
   

}
