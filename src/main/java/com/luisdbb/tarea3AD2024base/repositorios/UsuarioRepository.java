package com.luisdbb.tarea3AD2024base.repositorios;

import com.luisdbb.tarea3AD2024base.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	Usuario findByNombreAndPassword(String nombre, String password);
	
    String findRolByNombreAndPassword(String nombre, String password);
    
    Usuario findByNombre(String nombre);
    
    Usuario findNumeroUserPeregrinoByNombre(String nombre);
    
    Usuario findNumeroUserParadaByNombre(String nombre);
   
    
    
}