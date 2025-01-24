package com.luisdbb.tarea3AD2024base.repositorios;

import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PeregrinoRepository extends JpaRepository<Peregrino, Long> {
    // Buscar ID por nombre del peregrino
    @Query("SELECT p.id FROM Peregrino p WHERE p.nombre = :nombre")
    Long buscarIdPorNombre(String nombre);

    // Buscar peregrino por ID
    @Query("SELECT p FROM Peregrino p WHERE p.id = :id")
    Peregrino buscarPeregrinoPorId(Long id);

    
}
