package com.luisdbb.tarea3AD2024base.repositorios;

import com.luisdbb.tarea3AD2024base.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Buscar usuario por nombre de usuario y contraseña
	Usuario findByNombreAndPassword(String nombre, String password);
    // Buscar el rol por nombre de usuario y contraseña
    String findRolByNombreAndPassword(String nombre, String password);
    
    
    
}