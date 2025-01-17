package com.luisdbb.tarea3AD2024base.modelo;

import jakarta.persistence.*;

public class Usuario {
	private String nombre;
	private String password;
	private String correo;
	@Enumerated(EnumType.STRING)
    private Rol rol;

    @ManyToOne
    @JoinColumn(name = "numero_user_peregrino")
    private Peregrino peregrino;

    @ManyToOne
    @JoinColumn(name = "numero_user_parada")
    private Parada parada;
	
	

}
