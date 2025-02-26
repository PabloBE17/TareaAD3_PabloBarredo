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

    public Parada save(Parada entity) {
        return paradaRepository.save(entity);
    }

    public Parada update(Parada entity) {
        return paradaRepository.save(entity);
    }

    public void delete(Parada entity) {
        paradaRepository.delete(entity);
    }

    public void delete(Long id) {
        paradaRepository.deleteById(id);
    }

    public Parada find(Long id) {
        Optional<Parada> parada = paradaRepository.findById(id);
        return parada.orElse(null);
    }

    public List<Parada> findAll() {
        return paradaRepository.findAll();
    }

    public List<Parada> buscarParadas() {
        return paradaRepository.findAll();
    }

    public Parada findByNombre(String nombre) {
        List<Parada> paradas = paradaRepository.findByNombre(nombre);
        return paradas.isEmpty() ? null : paradas.get(0);
    }

    public boolean existeParadaPorNombre(String nombre) {
        List<Parada> paradas = paradaRepository.findByNombre(nombre);
        return !paradas.isEmpty(); 
    }

    public boolean existsById(Long id) {
        return paradaRepository.existsById(id);
    }

    public long count() {
        return paradaRepository.count();
    }
    public List<Parada> findByPeregrinoId(Long idPeregrino) {
        return paradaRepository.findByPeregrinosAlojados_Id(idPeregrino);
    }
    public Parada findParadaById(Long id) {
        return paradaRepository.findById(id).orElse(null);
    }
    
}