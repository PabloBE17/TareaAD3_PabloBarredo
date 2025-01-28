package com.luisdbb.tarea3AD2024base.modelo;
import com.luisdbb.tarea3AD2024base.modelo.Perfil;
public class Sesion {
	private static Sesion sesion;
	Perfil perfil;
	String nombre;
	private long id;
	public Sesion(Perfil perfil, String nombre, long id) {
		super();
		this.perfil = perfil;
		this.nombre = nombre;
		this.id = id;
	}
	
	public Sesion() {
		super();
	}

	public Perfil getPerfil() {
		return perfil;
	}
	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public static Sesion getSesion() {
		if(sesion ==null) {
			sesion=new Sesion();
		}
		return sesion;
	}
	
}
