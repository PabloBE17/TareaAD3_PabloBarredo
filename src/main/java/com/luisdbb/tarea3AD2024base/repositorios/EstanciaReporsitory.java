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
	List<Estancia> findByParadaIdAndFechaBetween(Long paradaId, LocalDate fechaInicio, LocalDate fechaFin);
	}