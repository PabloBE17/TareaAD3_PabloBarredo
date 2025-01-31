package com.luisdbb.tarea3AD2024base.services;

import java.time.LocalDate;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.Estancia;
import com.luisdbb.tarea3AD2024base.repositorios.EstanciaReporsitory;
@Service
public class EstanciaService {


    @Autowired
    private EstanciaReporsitory estanciaRepository;

    public List<Estancia> obtenerTodas() {
        return estanciaRepository.findAll();
    }

    public Optional<Estancia> obtenerPorId(Long id) {
        return estanciaRepository.findById(id);
    }

    public List<Estancia> obtenerPorFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        return estanciaRepository.findByFechaBetween(fechaInicio, fechaFin);
    }

    public List<Estancia> obtenerPorParada(Long paradaId) {
        return estanciaRepository.findByParadaId(paradaId);
    }

    public List<Estancia> obtenerPorPeregrino(Long peregrinoId) {
        return estanciaRepository.findByPeregrinoId(peregrinoId);
    }

    public List<Estancia> findByParadaIdAndFechaBetween(Long idParada, LocalDate inicio, LocalDate fin) {
        return estanciaRepository.findByParadaIdAndFechaBetween(idParada, inicio, fin);
    }

    public Estancia guardarEstancia(Estancia estancia) {
        return estanciaRepository.save(estancia);
    }

    public void eliminarEstancia(Long id) {
        estanciaRepository.deleteById(id);
    }
    public Estancia saveEstancia(Estancia estancia) {
        
        return estanciaRepository.save(estancia);
    }
}

