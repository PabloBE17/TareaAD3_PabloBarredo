package com.luisdbb.tarea3AD2024base.repositorios;


import com.luisdbb.tarea3AD2024base.modelo.Carnet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarnetRepository extends JpaRepository<Carnet, Long> {

    // Encuentra carnets por el ID del usuario asociado
    @Query("SELECT c FROM Carnet c WHERE c.usuario.id = :userId")
    List<Carnet> findByUserId(@Param("userId") Long userId);

    // Encuentra carnets por la parada inicial
    @Query("SELECT c FROM Carnet c WHERE c.paradaInicial.id = :paradaId")
    List<Carnet> findByParadaInicial(@Param("paradaId") Long paradaId);

    // Verifica si existe un carnet por ID
    boolean existsById(Long id);

	;
}