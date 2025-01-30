package com.luisdbb.tarea3AD2024base.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Rol;
import com.luisdbb.tarea3AD2024base.modelo.Usuario;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.services.UsuarioServicio;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

@Controller
public class AnadirParadaController implements Initializable {
	@Autowired
    private StageManager stageManager;

    @Autowired
    private ParadaService paradaService;
    @Autowired
    private UsuarioServicio usuarioService;


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

        Usuario usuario = new Usuario();
        usuario.setNombre(responsable);
        usuario.setPassword(contrasena); 
        usuario.setRol(Rol.PARADA);
        usuario.setParada(parada); 

        
        usuarioService.save(usuario);

        showAlert(Alert.AlertType.INFORMATION, "Éxito", "Parada y usuario creados correctamente.");
        
        limpiarCampos();
    }

    @FXML
    private void cancelarEdicion(ActionEvent event) {
        limpiarCampos();
        stageManager.switchScene(FxmlView.LOGIN);
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
