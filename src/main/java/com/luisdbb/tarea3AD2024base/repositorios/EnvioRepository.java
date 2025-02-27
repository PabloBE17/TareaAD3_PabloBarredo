package com.luisdbb.tarea3AD2024base.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.luisdbb.tarea3AD2024base.modelo.EnvioACasa;
import java.util.List;

@Repository
public interface EnvioRepository extends JpaRepository<EnvioACasa, Long> {
    
    List<EnvioACasa> findByIdParada(long idParada);
    
    List<EnvioACasa> findByUrgenteTrue();
    List<EnvioACasa> findAll();

}
