package com.luisdbb.tarea3AD2024base.repositorios;

import com.luisdbb.tarea3AD2024base.modelo.Estancia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EstanciaReporsitory extends JpaRepository<Estancia, Long> {
    
    List<Estancia> findByFechaBetween(LocalDate fechaInicio, LocalDate fechaFin);

    List<Estancia> findByParadaId(Long paradaId);

    List<Estancia> findByPeregrinoId(Long peregrinoId);
    
    List<Estancia> findByParadaIdAndFechaBetween(Long paradaId, LocalDate fechaInicio, LocalDate fechaFin);
    
	}