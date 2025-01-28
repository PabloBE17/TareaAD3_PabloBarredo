package com.luisdbb.tarea3AD2024base.services;

import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.repositorios.ParadaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParadaService {

    @Autowired
    private ParadaRepository paradaRepository;

    // Guardar una parada
    public Parada save(Parada entity) {
        return paradaRepository.save(entity);
    }

    // Actualizar una parada
    public Parada update(Parada entity) {
        return paradaRepository.save(entity);
    }

    // Eliminar una parada por instancia
    public void delete(Parada entity) {
        paradaRepository.delete(entity);
    }

    // Eliminar una parada por ID
    public void delete(Long id) {
        paradaRepository.deleteById(id);
    }

    // Buscar una parada por ID
    public Parada find(Long id) {
        Optional<Parada> parada = paradaRepository.findById(id);
        return parada.orElse(null);
    }

    // Obtener todas las paradas
    public List<Parada> findAll() {
        return paradaRepository.findAll();
    }

    // Buscar paradas (duplicado de findAll, puede ser eliminado si no se usa)
    public List<Parada> buscarParadas() {
        return paradaRepository.findAll();
    }

    // Buscar una parada por nombre y devolver su ID
    public Long buscarIdPorNombre(String nombre) {
        Parada parada = paradaRepository.findByNombre(nombre);
        return parada != null ? parada.getId() : null;
    }

    // Buscar una parada por nombre
    public Parada findByNombre(String nombre) {
        return paradaRepository.findByNombre(nombre);
    }

    // Verificar si existe una parada por ID
    public boolean existsById(Long id) {
        return paradaRepository.existsById(id);
    }

    // Contar el número total de paradas
    public long count() {
        return paradaRepository.count();
    }
}