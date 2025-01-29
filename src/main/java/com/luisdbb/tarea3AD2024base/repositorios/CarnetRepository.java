package com.luisdbb.tarea3AD2024base.repositorios;

import com.luisdbb.tarea3AD2024base.modelo.Carnet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CarnetRepository extends JpaRepository<Carnet, Long> {

    List<Carnet> findByParadaInicialId(Long paradaId);

    List<Carnet> findByFechaExpedicion(LocalDate fechaExpedicion);

    List<Carnet> findByFechaExpedicionBetween(LocalDate startDate, LocalDate endDate);

    Carnet getReferenceById(Long id);
}