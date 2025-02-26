package com.luisdbb.tarea3AD2024base.repositorios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;
import com.luisdbb.tarea3AD2024base.config.Db4oManager;
import com.luisdbb.tarea3AD2024base.modelo.ConjuntoContratado;
import com.luisdbb.tarea3AD2024base.modelo.Servicio;

import jakarta.annotation.PostConstruct;

@Repository
public class Db4oRepositorio {
	@Autowired
	private Db4oManager Db4oManager;
	private ObjectContainer db;
	@PostConstruct
	public void init() {
	    if (db == null || db.ext().isClosed()) {
	        db = Db4oManager.getDbo4(); 
	    }
	}
    
	public void save(Servicio obj) {
	    try {
	        if (db == null || db.ext().isClosed()) {  
	            db = Db4oManager.getDbo4();  
	        }
	        db.store(obj);
	        db.commit();
	    } catch (Exception e) {
	        if (!db.ext().isClosed()) {
	            db.rollback(); 
	        }
	        System.err.println("Error al guardar: " + e.getMessage());
	    }
	}
	public void saveConjunto(ConjuntoContratado obj) {
	    try {
	        if (db == null || db.ext().isClosed()) {  
	            db = Db4oManager.getDbo4();  
	        }
	        db.store(obj);
	        db.commit();
	    } catch (Exception e) {
	        if (!db.ext().isClosed()) {
	            db.rollback(); 
	        }
	        System.err.println("Error al guardar: " + e.getMessage());
	    }
	}
    public List<Servicio> verServicios() {
        Query query = db.query();
        query.constrain(Servicio.class);
        return new ArrayList<>(query.execute());
    }
    public void delete(Object obj) {
    	try {
	        db.delete(obj);  
	        db.commit();   
	    } catch (Exception e) {
	        db.rollback(); 
	        System.err.println("Error al borrar: " + e.getMessage());    	        
	    }
    }
    public static List<Servicio> buscarServicioPorNombre(ObjectContainer db, String nombre) {
        Query query = db.query();
        query.constrain(Servicio.class);
        query.descend("nombre").constrain(nombre); 

        ObjectSet<Servicio> result = query.execute();
        return new ArrayList<>(result); 
    }
    public static boolean existeServicio(ObjectContainer db, String nombre) {
        Query query = db.query();
        query.constrain(Servicio.class);
        query.descend("nombre").constrain(nombre);

        ObjectSet<Servicio> result = query.execute();
        
        return !result.isEmpty(); 
    }
    public long generarNuevoId() {
        Query query = db.query();
        query.constrain(Servicio.class);
        
        List<Servicio> servicios = new ArrayList<>(query.execute());
        
        if (servicios.isEmpty()) {
            return 1L; 
        }

        long maxId = servicios.stream()
                              .mapToLong(Servicio::getId)
                              .max()
                              .orElse(0);

        return maxId + 1; 
    }
    public long generarNuevoIdConjunto() {
        

        Query query = db.query();
        query.constrain(ConjuntoContratado.class);

        List<ConjuntoContratado> conjuntos = new ArrayList<>(query.execute());

        long maxId = conjuntos.stream()
                              .mapToLong(ConjuntoContratado::getId)
                              .max()
                              .orElse(0);

        return maxId + 1; 
    }
    public List<Servicio> obtenerServiciosPorParada(Long idParada) {
        Query query = db.query();
        query.constrain(Servicio.class);
        query.descend("idParadas").constrain(idParada).contains(); 
        
        return new ArrayList<>(query.execute());
    }
    
    


}
