package com.luisdbb.tarea3AD2024base.repositorios;

import com.luisdbb.tarea3AD2024base.modelo.Peregrino;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface PeregrinoRepository extends JpaRepository<Peregrino, Long> {
	Long findIdByNombre(String nombre);
	 Peregrino getById(Long id);
	
	
	 
    
}
