package com.luisdbb.tarea3AD2024base.repositorios;

import com.luisdbb.tarea3AD2024base.modelo.Parada;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParadaRepository extends JpaRepository<Parada, Long> {
	
    List<Parada> findByNombre(String nombre);

   
    Parada findFirstByNombre(String nombre);

    
    List<Parada> findByPeregrinosAlojados_Id(Long idPeregrino);
    
   
}
    
