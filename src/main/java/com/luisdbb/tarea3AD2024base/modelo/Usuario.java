package com.luisdbb.tarea3AD2024base.modelo;

import jakarta.persistence.*;
@Entity
@Table(name = "usuarios")
public class Usuario {


	    @Id
	    @Column(nullable = false, unique = true) // La clave primaria no debe repetirse
	    private String nombre;

	    @Column(nullable = false)
	    private String password;

	    @Column(nullable = false, unique = true) // Correo debe ser único
	    private String correo;

	    @Enumerated(EnumType.STRING)
	    private Rol rol;

	    @ManyToOne
	    @JoinColumn(name = "numero_user_peregrino") // Clave foránea hacia Peregrino
	    private Peregrino peregrino;

	    @ManyToOne
	    @JoinColumn(name = "numero_user_parada") // Clave foránea hacia Parada
	    private Parada parada;

	    // Getters y Setters
	    public String getNombre() {
	        return nombre;
	    }

	    public void setNombre(String nombre) {
	        this.nombre = nombre;
	    }

	    public String getPassword() {
	        return password;
	    }

	    public void setPassword(String password) {
	        this.password = password;
	    }

	    public String getCorreo() {
	        return correo;
	    }

	    public void setCorreo(String correo) {
	        this.correo = correo;
	    }

	    public Rol getRol() {
	        return rol;
	    }

	    public void setRol(Rol rol) {
	        this.rol = rol;
	    }

	    public Peregrino getPeregrino() {
	        return peregrino;
	    }

	    public void setPeregrino(Peregrino peregrino) {
	        this.peregrino = peregrino;
	    }

	    public Parada getParada() {
	        return parada;
	    }

	    public void setParada(Parada parada) {
	        this.parada = parada;
	    }

	    @Override
	    public String toString() {
	        return "Usuario [nombre=" + nombre + ", password=" + password + ", correo=" + correo
	                + ", rol=" + rol + ", peregrino=" + peregrino + ", parada=" + parada + "]";
	    }
	}