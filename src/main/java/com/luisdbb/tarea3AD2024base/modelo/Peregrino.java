package com.luisdbb.tarea3AD2024base.modelo;

import java.util.ArrayList;
import java.util.List; 
import jakarta.persistence.*;
@Entity
@Table(name = "Peregrino")
public class Peregrino {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = true, nullable = false)
	private long id;
	@Column(name = "nombre", updatable = true, nullable = false, unique=true)
	private String nombre;
	@Column(name = "nacionalidad", updatable = true, nullable = false)
	private String nacionalidad;
	@Column(name ="email",updatable  =true, nullable=false, unique=true)
	private String email;

	@OneToOne
    @JoinColumn(name = "id_carnet")
	private Carnet carnet;
	@OneToMany(mappedBy = "peregrino")
    private List<Estancia> estancias= new ArrayList<>();
	@ManyToMany(fetch = FetchType.EAGER) 
	@JoinTable(
	    name = "peregrinos_paradas", 
	    joinColumns = @JoinColumn(name = "peregrino_id"), 
	    inverseJoinColumns = @JoinColumn(name = "parada_id") 
	)
    private List<Parada> parada= new ArrayList<>();
    
    public Peregrino() {
		super();
	}
	public Peregrino(String nombre, long id, String nacionalidad, String email) {
		this.nombre = nombre;
		this.nacionalidad = nacionalidad;
		this.email=email;
		
	}
	

    
	public Peregrino(String nombre, long id, String nacionalidad, Carnet carnet) {
		super();
		this.nombre = nombre;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
