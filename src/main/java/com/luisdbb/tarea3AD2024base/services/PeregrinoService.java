package com.luisdbb.tarea3AD2024base.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.repositorios.PeregrinoRepository;
import com.luisdbb.tarea3AD2024base.repositorios.UsuarioRepository;

@Service

public class PeregrinoService {
	@Autowired
	private PeregrinoRepository peregrinoRepository;
	 @SuppressWarnings("unused")
	@Autowired
	    private UsuarioRepository usuarioRepository;
	 
	public Peregrino save(Peregrino entity) {
		return peregrinoRepository.save(entity);
	}
	public Peregrino update(Peregrino entity) {
		return peregrinoRepository.save(entity);
	}
	public void delete(Peregrino entity) {
		peregrinoRepository.delete(entity);
	}
	public void deletePorId(Long id) {
		peregrinoRepository.deleteById(id);
	}
	public Peregrino find(Long id) {
		return peregrinoRepository.findById(id).get();
	}
	public List<Peregrino> findAll() {
		return peregrinoRepository.findAll();
	}
	
}
