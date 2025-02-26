package com.luisdbb.tarea3AD2024base.controller;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Perfil;
import com.luisdbb.tarea3AD2024base.modelo.Rol;
import com.luisdbb.tarea3AD2024base.modelo.Sesion;
import com.luisdbb.tarea3AD2024base.modelo.Usuario;
import com.luisdbb.tarea3AD2024base.services.UsuarioServicio;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javafx.scene.image.ImageView;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
    private TextField visiblePassword;

    @FXML
    private ImageView togglePassword;
    @FXML
    private Button loginButton;
    @FXML
    private WebView webView;
    @FXML
    private ImageView userIcon;
    @FXML
    
    
    private boolean PasswordVisible = false;
    
    @FXML
    private void login(ActionEvent event) {
        String Username = username.getText();
        String Password;
        
        if (PasswordVisible) {
            Password = visiblePassword.getText();
        } else {
            Password = password.getText();
        }

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
    
    @FXML
    private void togglePasswordVisibility() {
        PasswordVisible = !PasswordVisible;
        if (PasswordVisible) {
            visiblePassword.setText(password.getText());
            visiblePassword.setVisible(true);
            visiblePassword.setManaged(true);
            password.setVisible(false);
            password.setManaged(false);
        } else {
            password.setText(visiblePassword.getText());
            password.setVisible(true);
            password.setManaged(true);
            visiblePassword.setVisible(false);
            visiblePassword.setManaged(false);
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	 visiblePassword.setManaged(false);
         visiblePassword.setVisible(false);
         webView.setVisible(false);
         
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
    @FXML
    private void mostrarAyuda() {
        try {
            // Cargar el archivo HTML desde los recursos
            URL url = getClass().getResource("/tarea3AD2024/src/main/resources/html/InicioSesionHtml.html");
            if (url == null) {
                throw new NullPointerException();
            }
            webView.getEngine().load(url.toExternalForm());

            // Hacer visible el WebView
            webView.setVisible(true);
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Archivo de Ayuda no encontrado");
            alert.setContentText("Por favor, verifica que el archivo 'help.html' esté en la ruta '/ayuda/'.");
            alert.showAndWait();
        }
    }
}