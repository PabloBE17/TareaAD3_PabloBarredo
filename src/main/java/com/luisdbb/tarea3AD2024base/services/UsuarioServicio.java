package com.luisdbb.tarea3AD2024base.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.modelo.Sesion;
import com.luisdbb.tarea3AD2024base.modelo.User;
import com.luisdbb.tarea3AD2024base.modelo.Usuario;
import com.luisdbb.tarea3AD2024base.repositorios.UsuarioRepository;


@Service

public class UsuarioServicio {
	@Autowired
	private UsuarioRepository usuarioRepository;
	 private static Usuario usuarioActual; 
	 private Sesion sesionActual = Sesion.getSesion();
	public Usuario save(Usuario entity) {
		return usuarioRepository.save(entity);
	}
	public Usuario update(Usuario entity) {
		return usuarioRepository.save(entity);
	}
	public void delete(Usuario entity) {
		usuarioRepository.delete(entity);
	}
	public void deletePorId(Long id) {
		usuarioRepository.deleteById(id);
	}
	public Usuario find(Long id) {
		return usuarioRepository.findById(id).get();
	}
	public List<Usuario> findAll() {
		return usuarioRepository.findAll();
	}
	public Usuario autenticarObtenerUser(String nombre, String password) {
        Usuario usuario = usuarioRepository.findByNombreAndPassword(nombre, password);
        if (usuario != null) {
            return usuario;
        }
        return null; 
    }

	public Long obtenerIdPeregrino(String nombre) {
	    Usuario usuario = usuarioRepository.findNumeroUserPeregrinoByNombre(nombre);

	    if (usuario == null) {
	        return null;
	    }

	    Peregrino peregrino = usuario.getPeregrino();

	    if (peregrino == null) {
	        return null;
	    }

	    return peregrino.getId();
	}
	
	


}
