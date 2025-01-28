package com.luisdbb.tarea3AD2024base.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.services.ParadaService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

@Controller
public class AñadirParadaController implements Initializable {

    @Autowired
    private ParadaService paradaService;

    @FXML
    private TextField nombreParadaField;

    @FXML
    private TextField regionField;

    @FXML
    private TextField responsableField;

    @FXML
    private TextField contrasenaField;

    @FXML
    private Button guardarButton;

    @FXML
    private Button cancelarButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    @FXML
    private void guardarParada(ActionEvent event) {
        String nombreParada = nombreParadaField.getText();
        String region = regionField.getText();
        String responsable = responsableField.getText();
        String contrasena = contrasenaField.getText();

        
        if (nombreParada.isEmpty() || region.isEmpty() || responsable.isEmpty() || contrasena.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Campos Vacíos", "Por favor, completa todos los campos obligatorios.");
            return;
        }

        
        if (region.length() != 1) {
            showAlert(Alert.AlertType.WARNING, "Error de Región", "La región debe ser un único carácter.");
            return;
        }

        
        Parada parada = new Parada();
        parada.setNombre(nombreParada);
        parada.setRegion(region.charAt(0));
        parada.setResponsable(responsable);

        
        paradaService.save(parada);

        showAlert(Alert.AlertType.INFORMATION, "Éxito", "Parada añadida correctamente.");

        
        limpiarCampos();
    }

    @FXML
    private void cancelarEdicion(ActionEvent event) {
        limpiarCampos();
    }

    private void limpiarCampos() {
        nombreParadaField.clear();
        regionField.clear();
        responsableField.clear();
        contrasenaField.clear();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
