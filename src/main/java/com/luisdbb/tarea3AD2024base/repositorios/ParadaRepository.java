package com.luisdbb.tarea3AD2024base.repositorios;

import com.luisdbb.tarea3AD2024base.modelo.Parada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParadaRepository extends JpaRepository<Parada, Long> {
    // Obtener todas las paradas
    @Query("SELECT p FROM Parada p")
    List<Parada> bucarParadas();

    // Buscar ID de parada por nombre
    @Query("SELECT p.id FROM Parada p WHERE p.nombre = :nombre")
    Long buscarIdPorNombre(String nombre);

    @Query("SELECT p FROM Parada p WHERE p.paradaInicial.id = :idParadaInicial")
    List<Parada> buscarParadasPorParadaInicial(@Param("idParadaInicial") Long idParadaInicial);
}
    
