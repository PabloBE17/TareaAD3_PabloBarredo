package com.luisdbb.tarea3AD2024base.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.ConjuntoContratado;
import com.luisdbb.tarea3AD2024base.modelo.Servicio;
import com.luisdbb.tarea3AD2024base.repositorios.Db4oRepositorio;
@Service
public class Db4oServicio {
	@Autowired
	private Db4oRepositorio db4oRepositorio;
	
	public void guardarServicio(Servicio servicio) {
	    if (servicio.getId() == null) {  
	        servicio.setId(db4oRepositorio.generarNuevoId());
	    }
	    db4oRepositorio.save(servicio);
	}
	
	public List<Servicio> obtenerTodosLosServicios() {
	    return db4oRepositorio.verServicios();
	}
	public List<Servicio> obtenerServiciosPorParada(Long idParada) {
	    return db4oRepositorio.obtenerServiciosPorParada(idParada);
	}
	public void guardarConjuntoContratado(ConjuntoContratado conjunto) {
        if (conjunto.getId() == 0) {  
            conjunto.setId(db4oRepositorio.generarNuevoIdConjunto());
        }
        db4oRepositorio.saveConjunto(conjunto);
    }
	 
}
