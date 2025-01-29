package com.luisdbb.tarea3AD2024base.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.Carnet;
import com.luisdbb.tarea3AD2024base.repositorios.CarnetRepository;

import java.util.List;

@Service
public class CarnetService {

    @Autowired
    private CarnetRepository carnetRepository;

    public Carnet save(Carnet entity) {
        return carnetRepository.save(entity);
    }

    public Carnet update(Carnet entity) {
        return carnetRepository.save(entity);
    }

    public void delete(Carnet entity) {
        carnetRepository.delete(entity);
    }

    public void delete(Long id) {
        carnetRepository.deleteById(id);
    }

    public Carnet find(Long id) {
        return carnetRepository.findById(id).orElse(null);
    }

    public List<Carnet> findAll() {
        return carnetRepository.findAll();
    }

    public boolean existsById(Long id) {
        return carnetRepository.existsById(id);
    }

    
    
}
