package com.luisdbb.tarea3AD2024base.repositorios;

import com.luisdbb.tarea3AD2024base.modelo.Estancia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EstanciaReporsitory extends JpaRepository<Estancia, Long> {
    // Obtener estancias por ID de parada y rango de fechas
    @Query("SELECT e FROM Estancia e WHERE e.parada.id = :paradaId AND e.fecha BETWEEN :fechaInicio AND :fechaFin")
    List<Estancia> buscarPorParadayFecha(Long paradaId, LocalDate fechaInicio, LocalDate fechaFin);
}