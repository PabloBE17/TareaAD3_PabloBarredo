package com.luisdbb.tarea3AD2024base.repositorios;

import com.luisdbb.tarea3AD2024base.modelo.Carnet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CarnetRepository extends JpaRepository<Carnet, Long> {

    // Encuentra carnets por la parada inicial
    List<Carnet> findByParadaInicialId(Long paradaId);

    // Encuentra carnets por fecha de expedición
    List<Carnet> findByFechaExpedicion(LocalDate fechaExpedicion);

    // Encuentra carnets en un rango de fechas
    List<Carnet> findByFechaExpedicionBetween(LocalDate startDate, LocalDate endDate);

    // Verifica si existe un carnet por ID (proxy)
    Carnet getReferenceById(Long id);
}