package com.luisdbb.tarea3AD2024base.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.luisdbb.tarea3AD2024base.modelo.EnvioACasa;
import com.luisdbb.tarea3AD2024base.repositorios.EnvioRepository;

import java.util.List;

@Service
public class EnvioService {
	

    @Autowired
    private EnvioRepository envioRepository;

    public void guardarEnvio(EnvioACasa envio) {
        envioRepository.save(envio);
    }

    public List<EnvioACasa> obtenerEnviosPorParada(long idParada) {
        return envioRepository.findByIdParada(idParada);
    }

    public List<EnvioACasa> obtenerEnviosUrgentes() {
        return envioRepository.findByUrgenteTrue();
    }
    public List<EnvioACasa> obtenerTodosLosEnvios() {
        return envioRepository.findAll();
    }
}