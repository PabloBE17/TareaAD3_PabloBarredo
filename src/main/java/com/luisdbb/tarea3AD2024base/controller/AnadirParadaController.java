package com.luisdbb.tarea3AD2024base.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

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
    
    @FXML
    private TextField correoResponsableField;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    @FXML
    private void guardarParada(ActionEvent event) {
        String nombreParada = nombreParadaField.getText();
        String region = regionField.getText();
        String responsable = responsableField.getText();
        String contrasena = contrasenaField.getText();
        String correoResponsable = correoResponsableField.getText();
        
        if (nombreParada.isEmpty() || region.isEmpty() || responsable.isEmpty() || contrasena.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Campos Vacíos", "Por favor, completa todos los campos.");
            return;
        }
        if (!esCorreoValido(correoResponsable)) {
            showAlert(Alert.AlertType.ERROR, "Correo Inválido", "Por favor, ingresa un correo válido.");
            return;
        }
        if (!esRegionValida(region)) {
            showAlert(Alert.AlertType.WARNING, "Error de Región", "La región debe ser una única letra .");
            return;
        }
        
        if (region.length() != 1) {
            showAlert(Alert.AlertType.WARNING, "Error de Región", "La región debe ser un único carácter.");
            return;
        }
        if (contieneEspacios(responsable) || contieneEspacios(contrasena)) {
            showAlert(Alert.AlertType.ERROR, "Error de Entrada", "El nombre de usuario y la contraseña no pueden contener espacios.");
            return;
        }
        
        if (paradaService.existeParadaPorNombre(nombreParada)) {
            showAlert(Alert.AlertType.ERROR, "Parada Duplicada", "Ya existe una parada con este nombre.");
            return;
        }
        if (usuarioService.existePorNombre(responsable)) {
            showAlert(Alert.AlertType.ERROR, "Usuario Duplicado", "El nombre del responsable ya está registrado.");
            return;
        }

        if (usuarioService.existePorCorreo(correoResponsable)) {
            showAlert(Alert.AlertType.ERROR, "Correo Duplicado", "El correo ingresado ya está en uso.");
            return;
        }
        
        Parada parada = new Parada();
        parada.setNombre(nombreParada);
        parada.setRegion(region.charAt(0));
        parada.setResponsable(responsable);

        
        paradaService.save(parada);

        Usuario usuario = new Usuario();
        usuario.setNombre(responsable);
        usuario.setCorreo(correoResponsable);
        usuario.setPassword(contrasena); 
        usuario.setRol(Rol.PARADA);
        usuario.setParada(parada); 

        
        usuarioService.save(usuario);

        showAlert(Alert.AlertType.INFORMATION, "Éxito", "Parada y usuario creados correctamente.");
        
        limpiarCampos();
        stageManager.switchScene(FxmlView.MENU_ADMIN);
    }

    @FXML
    private void cancelarEdicion(ActionEvent event) {
        limpiarCampos();
        stageManager.switchScene(FxmlView.MENU_ADMIN);
    }

    private void limpiarCampos() {
        nombreParadaField.clear();
        regionField.clear();
        responsableField.clear();
        contrasenaField.clear();
        correoResponsableField.clear();
    }
    private boolean esRegionValida(String region) {
        return region.matches("^[A-Za-z]$"); 
    }
    private boolean esCorreoValido(String correo) {
        String patronCorreo = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return Pattern.matches(patronCorreo, correo);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private boolean contieneEspacios(String input) {
        return input.contains(" ");
    }
}
