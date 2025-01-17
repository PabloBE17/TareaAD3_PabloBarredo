package com.luisdbb.tarea3AD2024base.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
@Entity
@Table(name = "Parada")
public class Parada implements Serializable {
	private static final long serialVersionUID = 1L;
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column(name = "id", updatable = false, nullable = false)
	 private long id;
	 @Column(name = "nombre", updatable = false, nullable = false)
	    private String nombre;
	 @Column(name = "region", updatable = false, nullable = false)
	    private char region;
	 @Column(name = "responsable", updatable = false, nullable = false)
	    private String responsable;
	    @ManyToMany(mappedBy = "paradas")
	    private List<Peregrino> peregrinosAlojados= new ArrayList<>(); 
	    @OneToMany(mappedBy = "parada")
	    private List<Estancia> estancias= new ArrayList<>(); 

	    
	    public Parada(long id, String nombre, char region, String responsable) {
	        this.id = id;
	        this.nombre = nombre;
	        this.region = region;
	        this.responsable = responsable;
	    }
	    

	    public Parada() {
			super();
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

	    public char getRegion() {
	        return region;
	    }

	    public void setRegion(char region) {
	        this.region = region;
	    }

	    public String getResponsable() {
	        return responsable;
	    }

	    public void setResponsable(String responsable) {
	        this.responsable = responsable;
	    }
	    
	    

		public List<Estancia> getEstancias() {
			return estancias;
		}


		public void setEstancias(List<Estancia> estancias) {
			this.estancias = estancias;
		}


		@Override
	    public String toString() {
	        return "Parada{" + "id=" + id + ", nombre=" + nombre + ", region=" + region + ", responsable=" + responsable + '}';
	    }
	    
}
