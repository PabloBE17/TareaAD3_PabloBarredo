package com.luisdbb.tarea3AD2024base.modelo;

import java.time.LocalDate;

import jakarta.persistence.*;
@Entity
@Table(name = "Estancia")
public class Estancia {
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column(name = "id", updatable = false, nullable = false)
	private long id;
	 @Column(name = "fecha",  nullable = false)
    private LocalDate fecha;
	 @Column(name = "vip",  nullable = false)
    private boolean vip;
	 @ManyToOne
	 @JoinColumn(name = "id_parada")
    private Parada parada;
	 @ManyToOne
	 @JoinColumn(name = "id_peregrino")
    private Peregrino peregrino;
    

    public Estancia(long id, LocalDate fecha, boolean vip, Parada parada,Peregrino peregrino) {
        this.id = id;
        this.fecha = fecha;
        this.vip = vip;
        this.parada = parada;
        this.peregrino = peregrino;
    }

    
    public Estancia() {
		super();
	}


	public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public boolean isVip() {
        return vip;
    }

    public void setVip(boolean vip) {
        this.vip = vip;
    }

    public Parada getParada() {
        return parada;
    }

    public void setParada(Parada parada) {
        this.parada = parada;
    }
    
    

	public Peregrino getPeregrino() {
		return peregrino;
	}


	public void setPeregrino(Peregrino peregrino) {
		this.peregrino = peregrino;
	}


	 @Override
	    public String toString() {
	        return "Estancia{" + "id=" + id + ", fecha=" + fecha + ", vip=" + vip + ", parada=" + parada + '}';
	    }
	
    

}
