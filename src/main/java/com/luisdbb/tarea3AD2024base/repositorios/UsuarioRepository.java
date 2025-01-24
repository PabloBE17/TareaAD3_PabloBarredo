package com.luisdbb.tarea3AD2024base.repositorios;

import com.luisdbb.tarea3AD2024base.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Buscar usuario por nombre de usuario y contraseña
    @Query("SELECT u FROM Usuario u WHERE u.nombre = :nombre AND u.password = :password")
    Usuario buscarNombrePorUsuarioyContraseña(String nombre, String password);

    // Buscar el rol por nombre de usuario y contraseña
    @Query("SELECT u.rol FROM Usuario u WHERE u.nombre = :nombre AND u.password = :password")
    String buscarRolPorUsuarioyContraseña(String nombre, String password);
    
    
    
}