package com.luisdbb.tarea3AD2024base.modelo;

import java.util.ArrayList;
import java.util.List; 
import jakarta.persistence.*;
@Entity
@Table(name = "Peregrino")
public class Peregrino {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private long id;
	@Column(name = "nomb  re", updatable = false, nullable = false)
	private String nombre;
	@Column(name = "nacionalidad", updatable = false, nullable = false)
	private String nacionalidad;
	@OneToOne
    @JoinColumn(name = "id_carnet")
	private Carnet carnet;
	@OneToMany(mappedBy = "peregrino")
    private List<Estancia> estancias= new ArrayList<>();
    private List<Parada> parada= new ArrayList<>();
    
    public Peregrino() {
		super();
	}
	public Peregrino(String nombre, long id, String nacionalidad) {
		this.nombre = nombre;
		this.id = id;
		this.nacionalidad = nacionalidad;
		
	}
	

    
	public Peregrino(String nombre, long id, String nacionalidad, Carnet carnet) {
		super();
		this.nombre = nombre;
		this.id = id;
		this.nacionalidad = nacionalidad;
		
	}
	public String getNacionalidad() {
		return nacionalidad;
	}
	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public Carnet getCarnet() {
		return carnet;
	}
	public void setCarnet(Carnet carnet) {
		this.carnet = carnet;
	}
	
	public List<Parada> getParada() {
		return parada;
	}
	public void setParada(List<Parada> parada) {
		this.parada = parada;
	}
	
	public List<Estancia> getEstancias() {
		return estancias;
	}
	public void setEstancias(List<Estancia> estancias) {
		this.estancias = estancias;
	}
	@Override
    public String toString() {
        return "Peregrino{" + "nombre=" + nombre + ", id=" + id + ", nacionalidad=" + nacionalidad + ", carnet=" + carnet + '}';
    }
	
	
	 
	

}
