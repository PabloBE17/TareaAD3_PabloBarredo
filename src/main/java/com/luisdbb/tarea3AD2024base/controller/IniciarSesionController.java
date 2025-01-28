package com.luisdbb.tarea3AD2024base.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.modelo.Usuario;
import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Rol;
import com.luisdbb.tarea3AD2024base.services.UsuarioServicio;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

@Controller
public class IniciarSesionController implements Initializable {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private StageManager stageManager;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button loginButton;

    @FXML
    private ImageView userIcon;

    @FXML
    private ImageView passwordIcon;

    @FXML
    private void login(ActionEvent event) {
        String Username = username.getText();
        String Password = password.getText();

        if (Username.isEmpty() || Password.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Campos Vacíos", "Por favor, completa todos los campos.");
            return;
        }

        Usuario usuario = usuarioServicio.autenticarObtenerUser(Username, Password);

        if (usuario != null) {
            if (usuario.getRol() == Rol.PEREGRINO) {
                showAlert(Alert.AlertType.INFORMATION, "Bienvenido", "Bienvenido, peregrino " + usuario.getNombre() + "!");
                stageManager.switchScene(FxmlView.AÑADIR_PEREGRINO); 
                
                
            } 
            else if(usuario.getRol() == Rol.ADMINISTRADOR) {
            	showAlert(Alert.AlertType.INFORMATION, "Bienvenido", "Bienvenido, administrador " + usuario.getNombre() + "!");
                stageManager.switchScene(FxmlView.AÑADIR_PARADA); 
            	
            }
            	else {
                showAlert(Alert.AlertType.INFORMATION, "Bienvenido", "Bienvenido, " + usuario.getNombre() + "!");
                
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Inicio de Sesión Fallido", "Nombre de usuario o contraseña incorrectos.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    private void AñadirPere(ActionEvent event) {
        // Cambiar a la vista de "Añadir Peregrino"
        stageManager.switchScene(FxmlView.AÑADIR_PEREGRINO);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}