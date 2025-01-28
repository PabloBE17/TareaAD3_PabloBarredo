package com.luisdbb.tarea3AD2024base.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.User;
import com.luisdbb.tarea3AD2024base.modelo.Usuario;
import com.luisdbb.tarea3AD2024base.repositorios.UsuarioRepository;


@Service

public class UsuarioServicio {
	@Autowired
	private UsuarioRepository usuarioRepository;
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
	public boolean authenticate(String username, String password) {
		Usuario usuario = usuarioRepository.findByNombreAndPassword(username,password);
		if (usuario == null) {
			return false;
		} else {
			if (password.equals(usuario.getPassword()))
				return true;
			else
				return false;
		}
	}
	public Usuario autenticarObtenerUser(String nombre, String password) {
        Usuario usuario = usuarioRepository.findByNombreAndPassword(nombre, password);
        if (usuario != null) {
            return usuario;
        }
        return null; 
    }


}
