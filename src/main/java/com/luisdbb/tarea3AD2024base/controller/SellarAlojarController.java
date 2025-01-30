package com.luisdbb.tarea3AD2024base.controller;


import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Sesion;
import com.luisdbb.tarea3AD2024base.services.ParadaService;


import javafx.fxml.FXML;
import javafx.scene.control.Label;

@Controller
public class SellarAlojarController {
	@Autowired
    private ParadaService paradaService;

    @FXML
    private Label nombreLabel;

    @FXML
    private Label regionLabel;

    @FXML
    private Label responsableLabel;

    public void initialize(URL location, ResourceBundle resources) {
        cargarDatosParada();
    }

    private void cargarDatosParada() {
        Long idParada = Sesion.getSesion().getId();

        if (idParada != null) {
            Parada parada = paradaService.findParadaById(idParada);

            if (parada != null) {
                nombreLabel.setText(parada.getNombre());
                regionLabel.setText(String.valueOf(parada.getRegion())); 
                responsableLabel.setText(parada.getResponsable());
            } else {
                setLabelText("Parada no encontrada");
            }
        } else {
            setLabelText("ID no válido");
        }
    }

    private void setLabelText(String text) {
        nombreLabel.setText(text);
        regionLabel.setText(text);
        responsableLabel.setText(text);
    }
}
