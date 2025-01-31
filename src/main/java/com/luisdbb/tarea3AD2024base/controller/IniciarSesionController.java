package com.luisdbb.tarea3AD2024base.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Usuario;
import com.luisdbb.tarea3AD2024base.modelo.Perfil;
import com.luisdbb.tarea3AD2024base.modelo.Rol;
import com.luisdbb.tarea3AD2024base.modelo.Sesion;
import com.luisdbb.tarea3AD2024base.services.UsuarioServicio;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

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

        
        if (validarAdministrador(Username, Password)) {
            showAlert(Alert.AlertType.INFORMATION, "Bienvenido", "Bienvenido, Administrador!");
            Sesion.getSesion().setId(0L);
            Sesion.getSesion().setNombre(Username);
            Sesion.getSesion().setPerfil(Perfil.ADMINISTRADOR);
            stageManager.switchScene(FxmlView.MENU_ADMIN);
            return;
        }

        Usuario usuario = usuarioServicio.autenticarObtenerUser(Username, Password);
        

        if (usuario != null) {
            if (usuario.getRol() == Rol.PEREGRINO) {
                showAlert(Alert.AlertType.INFORMATION, "Bienvenido", "Bienvenido, peregrino " + usuario.getNombre() + "!");
                long idPeregrino = usuarioServicio.obtenerIdPeregrino(Username);
                Sesion.getSesion().setId(idPeregrino);
                Sesion.getSesion().setNombre(Username);
                Sesion.getSesion().setPerfil(Perfil.PEREGRINO);
                
                stageManager.switchScene(FxmlView.MENU_PEREGRINO); 
            
            } else {
                showAlert(Alert.AlertType.INFORMATION, "Bienvenido", "Bienvenido, administrador de parada " + usuario.getNombre() + "!");
                
                long idParada = usuarioServicio.obtenerIdParadaPorNombreUsuario(Username);
                Sesion.getSesion().setId(idParada);
                Sesion.getSesion().setNombre(Username);
                Sesion.getSesion().setPerfil(Perfil.PARADA);
                stageManager.switchScene(FxmlView.MENU_PARADA);
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
        stageManager.switchScene(FxmlView.AÑADIR_PEREGRINO);
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    private boolean validarAdministrador(String usuario, String contraseña) {
        Properties propiedades = new Properties();
        try (InputStream input = getClass().getResourceAsStream("/Admin.properties")) {
            if (input == null) {
                System.err.println("No se encontró el archivo admin.properties");
                return false;
            }
            propiedades.load(input);
            String userAdmin = propiedades.getProperty("useradmin");
            String passAdmin = propiedades.getProperty("passadmin");

            return usuario.equals(userAdmin) && contraseña.equals(passAdmin);
        } catch (IOException e) {
            System.err.println("Error al leer admin.properties: " + e.getMessage());
            return false;
        }
    }
}